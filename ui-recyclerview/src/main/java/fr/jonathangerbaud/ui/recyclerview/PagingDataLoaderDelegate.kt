package fr.jonathangerbaud.ui.recyclerview

import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fr.jonathangerbaud.core.ext.d
import fr.jonathangerbaud.network.Resource
import fr.jonathangerbaud.ui.state.UIStateManager
import java.lang.ref.WeakReference


class PagingDataLoaderDelegate<T>(
    callback: PaginationDataLoaderCallback<T>,
    private var stateManager: UIStateManager,
    private val adapter: IPaginationAdapter,
    private val factory: (nextPageData: NextPageData) -> MutableLiveData<Resource<T>>
) : DefaultLifecycleObserver
{
    private val callback: WeakReference<PaginationDataLoaderCallback<T>> = WeakReference(callback)

    private var data: MutableLiveData<Resource<T>>? = null

    private var dataLoaded: Boolean = false

    val nextPageData = NextPageData(0)

    private lateinit var paginationDelegate:PaginationDelegate

    init
    {
        initViews(stateManager)

        callback.lifecycle.addObserver(this)
    }

    private fun initViews(stateManager: UIStateManager)
    {
        this.stateManager = stateManager

        var recyclerView:RecyclerView? = null

        stateManager.getDataView()?.let {

            if (it is RecyclerView)
                recyclerView = it
            else if (it is ViewGroup)
            {
                for(i in 0 until it.childCount)
                {
                    if (it.getChildAt(i) is RecyclerView)
                    {
                        recyclerView = it.getChildAt(i) as RecyclerView
                        break
                    }
                }
            }

            if (recyclerView == null)
                throw IllegalStateException("PagingDataLoaderDelegate : Couldn't find any RecyclerView in StateManager's data view")


            if (it is SwipeRefreshLayout)
                it.setOnRefreshListener { load(true) }
        }

        stateManager.getDataStateView()?.setRetryCallback { load(true) }

        paginationDelegate = PaginationDelegate(recyclerView!!) { page ->
            d("pagination delegate says page = $page")
            nextPageData.page = page
            loadNext()
        }

        adapter.setErrorRetryCallback { loadNext() }
    }

    override fun onResume(owner: LifecycleOwner)
    {
        load()
    }

    private val observer = Observer<Resource<T>>
    {
        when (it.status)
        {
            Resource.SUCCESS ->
            {
                this.callback.get()?.let { callback ->
                    if (it.data is Collection<*> && (it.data as Collection<*>).isEmpty())
                        stateManager.setState(UIStateManager.State.EMPTY)
                    else
                        stateManager.setState(UIStateManager.State.DATA)

                    paginationDelegate.reset()

                    callback.onDataLoaded(it.data, true)

                    dataLoaded = true
                }
            }
            Resource.LOADING ->
            {
            }
            Resource.ERROR ->
            {
                stateManager.setState(UIStateManager.State.ERROR)
            }
        }
    }

    fun load(force: Boolean = false)
    {
        if (!dataLoaded || force)
        {
            stateManager.setState(
                    if (dataLoaded)
                        UIStateManager.State.LOADING
                    else
                        UIStateManager.State.LOADING_FIRST
            )

            data = factory(NextPageData(0))
            data!!.observe(callback.get()!!, observer)
        }
    }

    private val nextObserver = Observer<Resource<T>> {
        when (it.status)
        {
            Resource.LOADING -> adapter.showLoading()
            Resource.ERROR -> adapter.showError()
            Resource.SUCCESS ->
            {
                adapter.showLoading(false)
                this.callback.get()?.onDataLoaded(it.data)
            }
        }
    }

    private fun loadNext()
    {
        data = factory(nextPageData)
        data!!.observe(callback.get()!!, nextObserver)
    }

    fun setStateManager(stateManager: UIStateManager)
    {
        initViews(stateManager)

        if (dataLoaded)
            stateManager.setState(UIStateManager.State.DATA)
    }

    fun setNextPageToken(token: String?)
    {
        nextPageData.token = token
    }

    fun setNextPageData(data: Any?)
    {
        nextPageData.data = data
    }

    data class NextPageData(var page: Int, var token: String? = null, var data: Any? = null)

    interface PaginationDataLoaderCallback<T> : LifecycleOwner
    {
        fun onDataLoaded(data: T?, isReset:Boolean = false)
    }
}


