package fr.jonathangerbaud.ui.widgets

import android.content.Context
import android.view.View
import android.widget.CheckBox

open class CheckboxBuilder<T : CheckboxBuilder<T>> : CompoundButtonBuilder<T>()
{
    override fun createView(context: Context): View
    {
        return CheckBox(context)
    }
}