package fr.jonathangerbaud.ui.recyclerview

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView.Adapter
import fr.jonathangerbaud.core.ext.d
import fr.jonathangerbaud.core.ext.w
import kotlin.reflect.KClass


open class RendererAdapter : Adapter<Renderer<Any>>(), DataAdapter, IPaginationAdapter
{
    protected val DEFAULT_TYPE = -1

    private val viewHolderList: MutableList<(parent: ViewGroup) -> Renderer<*>> = mutableListOf()
    private val typeMap: MutableMap<KClass<*>, Int> = HashMap()

    var data: MutableList<Any> = mutableListOf()
        set(value)
        {
            field = value
            notifyDataSetChanged()
            liveData.value = value
        }

    val liveData: MutableLiveData<List<Any>> = MutableLiveData()

    fun add(element:Any, notify:Boolean = true)
    {
        data.add(element)

        if (notify)
            notifyItemInserted(data.size - 1)
    }

    fun addAll(elements:Collection<Any>, notify:Boolean = true)
    {
        val start = data.size

        data.addAll(elements)

        if (notify)
            notifyItemRangeInserted(start, elements.size)
    }

    fun remove(element:Any, notify:Boolean = true)
    {
        val position = data.indexOf(element)
        if (position != -1)
        {
            data.removeAt(position)
            if (notify)
                notifyItemRemoved(position)
        }
    }

    fun removeAll(elements:Collection<Any>, notify:Boolean = true)
    {
        elements.forEach { remove(it, notify) }
    }

    fun remove(from:Int, count:Int, notify: Boolean = true)
    {
        data = data.slice(0 until from).toMutableList()

        if (notify)
            notifyItemRangeRemoved(from, count)
    }

    fun addView(type: KClass<*>, factory: (parent: ViewGroup) -> Renderer<*>)
    {
        viewHolderList.add(factory)
        typeMap[type] = viewHolderList.size - 1
    }

    override fun getItemViewType(position: Int): Int
    {
        val type: KClass<Any> = data[position].javaClass.kotlin

        if (typeMap.containsKey(type))
            return typeMap.getValue(type)

        return DEFAULT_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Renderer<Any>
    {
        return if (viewType == DEFAULT_TYPE)
        {
            w(null, "A rendered is missing in adapter, cannot render view for some type of data")
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

    override fun getDataForPosition(position: Int): Any
    {
        return data[position]
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
        addView(LoadingItem::class) { parent -> LoadingRenderer(parent) }
        addView(ErrorItem::class) { parent -> ErrorRenderer(parent) }
    }

    override fun setPaginationDelegate(paginationDelegate: PaginationDelegate)
    {
        paginationDelegate.addCallback { page ->
            d("pagination says next page")
            setLoading(true) }
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

    override fun setLoading(loading: Boolean)
    {
        var position = data.indexOf(errorItem)
        if (position != -1)
        {
            d("remove error item")
            data.remove(errorItem)
            notifyItemRemoved(position)
        }

        if (loading)
        {
            d("add loading item 0")
            if (!data.contains(loadingItem))
            {
                d("add loading item 1")
                data.add(loadingItem)
                notifyItemInserted(data.size - 1)
            }
        }
        else
        {
            d("remove loading item 0")
            position = data.indexOf(loadingItem)
            if (position != -1)
            {
                d("remove loading item 1")
                data.removeAt(position)
                notifyItemRemoved(position)

        }
        }
    }

    override fun setError()
    {
        setLoading(false)

        errorItem = ErrorItem(errorText, errorCallback)
        d("add error item")
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
