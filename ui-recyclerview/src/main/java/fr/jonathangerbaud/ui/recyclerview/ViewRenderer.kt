package fr.jonathangerbaud.ui.recyclerview

import android.view.View


abstract class ViewRenderer<T, R: View> (view:R) : Renderer<T>(view)
{
    val view:R = itemView as R
}