package fr.jonathangerbaud.ui.recyclerview

import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlin.reflect.KClass


open class RendererAdapter : Adapter<Renderer<Any>>(), DataAdapter
{
    protected val DEFAULT_TYPE = 0

    private val viewHolderMap: MutableList<(parent:ViewGroup) -> Renderer<*>> = mutableListOf()
    private val typeMap: MutableMap<KClass<*>, Int> = HashMap()

    var data: MutableList<Any> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
            liveData.value = value
        }

    val liveData: MutableLiveData<List<Any>> = MutableLiveData()

    fun addView(type: KClass<*>, factory: (parent:ViewGroup) -> Renderer<*>)
    {
        viewHolderMap.add(factory)
        typeMap[type] = viewHolderMap.size - 1
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
        return viewHolderMap[viewType](parent) as Renderer<Any>
    }

    override fun onBindViewHolder(holder: Renderer<Any>, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getDataForPosition(position: Int): Any
    {
        return data[position]
    }
}