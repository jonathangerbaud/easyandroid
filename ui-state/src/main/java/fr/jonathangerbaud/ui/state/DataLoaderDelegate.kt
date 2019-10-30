package fr.jonathangerbaud.ui.state

import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fr.jonathangerbaud.core.ext.d
import fr.jonathangerbaud.network.Resource
import java.lang.ref.WeakReference

class DataLoaderDelegate<T>(
    callback: DataLoaderCallback<T>,
    private var stateManager: UIStateManager,
    private val factory: () -> LiveData<Resource<T>>
) : DefaultLifecycleObserver, Observer<Resource<T>>
{
    private val callback: WeakReference<DataLoaderCallback<T>> = WeakReference(callback)

    private var data: LiveData<Resource<T>>? = null

    private var dataLoaded: Boolean = false

    init
    {
        initViews(stateManager)

        callback.lifecycle.addObserver(this)
    }

    private fun initViews(stateManager: UIStateManager)
    {
        this.stateManager = stateManager

        stateManager.getDataView()?.let {
            if (it is SwipeRefreshLayout)
                it.setOnRefreshListener { load(true) }
        }

        stateManager.getDataStateView()?.setRetryCallback { load(true) }
    }

    override fun onResume(owner: LifecycleOwner)
    {
        load(false)
    }

    fun load(force: Boolean)
    {
        if (!dataLoaded || force)
        {
            stateManager.setState(
                if (dataLoaded) UIStateManager.State.LOADING
                else
                    UIStateManager.State.LOADING_FIRST
            )

            data = factory()
            data!!.observe(callback.get()!!, this)
        }
    }

    fun setStateManager(stateManager: UIStateManager)
    {
        initViews(stateManager)

        if (dataLoaded)
            stateManager.setState(UIStateManager.State.DATA)
    }

    override fun onChanged(resource: Resource<T>)
    {
        when (resource.status)
        {
            Resource.SUCCESS ->
            {
                if (callback.get() != null)
                {
                    if (resource.data is Collection<*> && (resource.data as Collection<*>).isEmpty())
                        stateManager.setState(UIStateManager.State.EMPTY)
                    else
                        stateManager.setState(UIStateManager.State.DATA)

                    callback.get()!!.onDataLoaded(resource.data)

                    dataLoaded = true
                }
            }
            Resource.LOADING ->
            {

            }
            Resource.ERROR   ->
            {
                stateManager.setState(UIStateManager.State.ERROR)
            }
        }
    }

    interface DataLoaderCallback<T> : LifecycleOwner
    {
        fun onDataLoaded(data: T?)
    }
}
