package fr.jonathangerbaud.ui.recyclerview

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import fr.jonathangerbaud.core.ext.d
import fr.jonathangerbaud.core.ext.w
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


open class RendererAdapter : Adapter<Renderer<Any>>(), DataAdapter, IPaginationAdapter
{
    companion object
    {
        const val MODE_TYPE = 0
        const val MODE_INSTANCE_OF = 1
    }
    protected val DEFAULT_TYPE = -1

    private val viewHolderList: MutableList<(parent: ViewGroup) -> Renderer<*>> = mutableListOf()
    private val typeMap: MutableMap<KClass<*>, Int> = HashMap()

    private val data: MutableList<Any> = mutableListOf()
        /*set(value)
        {
            field = value
            notifyDataSetChanged()
            liveData.value = value
        }*/

    val liveData: MutableLiveData<List<Any>> = MutableLiveData()
    var mode = MODE_TYPE

    init
    {
        liveData.value = data
    }

    fun add(element: Any, notify: Boolean = true)
    {
        data.add(element)

        if (notify)
            notifyItemInserted(data.size - 1)
    }

    fun addAll(elements: Collection<Any>, notify: Boolean = true)
    {
        val start = data.size

        data.addAll(elements)

        if (notify)
            notifyItemRangeInserted(start, elements.size)
    }

    fun remove(element: Any, notify: Boolean = true)
    {
        val position = data.indexOf(element)
        if (position != -1)
        {
            data.removeAt(position)
            if (notify)
                notifyItemRemoved(position)
        }
    }

    fun removeAll(elements: Collection<Any>, notify: Boolean = true)
    {
        elements.forEach { remove(it, notify) }
    }

    fun remove(from: Int, count: Int, notify: Boolean = true)
    {
        val max = Math.min(from + count, data.size)

        for (i in 0 until max)
            data.removeAt(from)

        if (notify)
            notifyItemRangeRemoved(from, max)
    }

    fun clear()
    {
        data.clear()
        notifyDataSetChanged()
    }

    inline fun <reified T : Any> addRenderer(noinline factory:(parent:ViewGroup) -> Renderer<T>)
    {
        addRenderer(T::class, factory)
    }

    fun addRenderer(type: KClass<*>, factory: (parent: ViewGroup) -> Renderer<*>)
    {
        viewHolderList.add(factory)
        typeMap[type] = viewHolderList.size - 1
    }

    override fun getItemViewType(position: Int): Int
    {
        if (mode == MODE_INSTANCE_OF)
        {
            val item = data[position]

            for (type in typeMap.keys)
            {
                if (type.isInstance(item))
                    return typeMap.getValue(type)
            }
        }
        else
        {
            val type: KClass<Any> = data[position].javaClass.kotlin

            if (typeMap.containsKey(type))
                return typeMap.getValue(type)
        }

        return DEFAULT_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Renderer<Any>
    {
        return if (viewType == DEFAULT_TYPE)
        {
            w(null, "A renderer is missing in adapter, cannot render view for some type of data")
            DummyRenderer(parent)
        }
        else
        {
            @Suppress("UNCHECKED_CAST")
            viewHolderList[viewType](parent) as Renderer<Any>
        }
    }

    override fun onBindViewHolder(holder: Renderer<Any>, position: Int)
    {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int
    {
        return data.size
    }

    override fun getDataAtPosition(position: Int): Any?
    {
        if (position >= 0 && position < data.size)
            return data[position]

        return null
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.layoutManager?.let {
            if (it is GridLayoutManager/* || it is StaggeredGridLayoutManager*/)
            {
                val currentSpanSizeLookup: GridLayoutManager.SpanSizeLookup = it.spanSizeLookup

                it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup()
                {
                    override fun getSpanSize(position: Int): Int
                    {
                        if (position >= 0 && (data[position] is LoadingItem || data[position] is ErrorItem))
                        {
                            return it.spanCount
                        }

                        return currentSpanSizeLookup.getSpanSize(position)
                    }
                }
            }
        }
    }

    class DummyRenderer(parent: ViewGroup) : ViewRenderer<Any, View>(View(parent.context))
    {
        init
        {
            itemView.setBackgroundColor(0xFFFF0000.toInt())
        }

        override fun bind(data: Any, position: Int)
        {
            // Do nothing
        }
    }

    // ############################
    // # IPaginationAdapter stuff #
    // ############################

    private var errorText: String? = null
    private var errorCallback: (() -> Unit)? = null

    private val loadingItem = LoadingItem()
    private var errorItem = ErrorItem()

    init
    {
        addRenderer(::LoadingRenderer)
        addRenderer(::ErrorRenderer)
    }

    override fun setPaginationDelegate(paginationDelegate: PaginationDelegate)
    {
        paginationDelegate.addCallback { page ->
            showLoading(true)
        }
    }

    override fun setLoadingView(view: View)
    {

    }

    override fun setLoadingView(layoutRes: Int)
    {

    }

    override fun setErrorView(view: View)
    {

    }

    override fun setErrorView(layoutRes: Int)
    {

    }

    override fun setErrorRetryCallback(callback: () -> Unit)
    {
        errorCallback = callback
    }

    override fun showLoading(loading: Boolean)
    {
        var position = data.indexOf(errorItem)
        if (position != -1)
        {
            data.remove(errorItem)
            notifyItemRemoved(position)
        }

        if (loading)
        {
            if (!data.contains(loadingItem))
            {
                data.add(loadingItem)
                notifyItemInserted(data.size - 1)
            }
        }
        else
        {
            position = data.indexOf(loadingItem)
            if (position != -1)
            {
                data.removeAt(position)
                notifyItemRemoved(position)

            }
        }
    }

    override fun showError()
    {
        showLoading(false)

        errorItem = ErrorItem(errorText, errorCallback)
        data.add(errorItem)
        notifyItemInserted(data.size - 1)
    }
}

private class LoadingItem

private class LoadingRenderer(parent: ViewGroup) : Renderer<LoadingItem>(parent, R.layout.item_loading)
{
    override fun bind(data: LoadingItem, position: Int)
    {

        // Do absolutely nothing!
    }
}

private class ErrorItem(val errorText: String? = null, val callback: (() -> Unit)? = null)

private class ErrorRenderer(parent: ViewGroup) : Renderer<ErrorItem>(parent, R.layout.item_error)
{
    private val text: TextView = itemView.findViewById(R.id.text)
    private val button: Button = itemView.findViewById(R.id.button)

    override fun bind(data: ErrorItem, position: Int)
    {
        data.errorText?.let { text.text = it }
        data.callback?.let { button.setOnClickListener { it() } }


    }
}
