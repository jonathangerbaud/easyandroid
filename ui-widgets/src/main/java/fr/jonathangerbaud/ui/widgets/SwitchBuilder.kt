package fr.jonathangerbaud.ui.widgets

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.SwitchCompat

open class SwitchBuilder<T : SwitchBuilder<T>> : WidgetBuilder<T>()
{
    override fun buildView(context: Context): View
    {
        val view = SwitchCompat(context)
        applyViewAttributes(view)

        return view
    }
}