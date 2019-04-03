package fr.jonathangerbaud.ui.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import fr.jonathangerbaud.ui.core.ext.inflate


/*abstract class Renderer<T>(view:View) : RecyclerView.ViewHolder(view)
{
    abstract fun bindView(view:View, data: T, position: Int)
}*/

abstract class Renderer<T>(view:View) : RecyclerView.ViewHolder(view)
{
    constructor(parent:ViewGroup, @LayoutRes layoutRes: Int) : this(parent.inflate(layoutRes))

    abstract fun bind(data: T, position: Int)
}