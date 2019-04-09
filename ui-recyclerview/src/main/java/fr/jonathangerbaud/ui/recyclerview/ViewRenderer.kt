package fr.jonathangerbaud.ui.recyclerview

import android.view.View


abstract class ViewRenderer<T, R: View> (view:R) : Renderer<T>(view)
{
    @Suppress("UNCHECKED_CAST")
    val view:R = itemView as R
}