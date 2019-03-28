package fr.jonathangerbaud.ui.widgets

import android.content.Context
import android.view.View
import android.widget.CheckBox

open class CheckboxBuilder<T : CheckboxBuilder<T>> : WidgetBuilder<T>()
{
    override fun buildView(context: Context): View
    {
        val view = CheckBox(context)
        applyViewAttributes(view)

        return view
    }
}