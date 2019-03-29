package fr.jonathangerbaud.ui.widgets

import android.view.View
import android.widget.CompoundButton


abstract class CompoundButtonBuilder<T : CompoundButtonBuilder<T>> : TextBuilder<T>()
{
    private var checked:Boolean? = null

    @Suppress("NAME_SHADOWING")
    override fun applyViewAttributes(view: View)
    {
        super.applyViewAttributes(view)
        val view = view as CompoundButton

        checked?.let { view.isChecked = it }
    }

    fun isChecked(isChecked: Boolean) = applySelf { checked = isChecked }
}