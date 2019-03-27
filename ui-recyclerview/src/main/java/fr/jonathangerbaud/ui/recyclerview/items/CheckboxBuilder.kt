package fr.jonathangerbaud.ui.recyclerview.items

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.CheckBox

class CheckboxBuilder : WidgetBuilder()
{
    init {
        measurements(CheckboxMeasurements())
    }

    override fun buildView(context: Context): View
    {
        val view = CheckBox(context)
        applyViewAttributes(view)

        return view
    }

    private class CheckboxMeasurements : DefaultMeasurements()
    {
        override fun getMinListItemHeight():Int
        {
            return SIZE_56
        }

        override fun getVerticalGravity(): Int {
            return Gravity.CENTER_VERTICAL
        }

        override fun getEndMargin(): Int {
            return SIZE_32
        }
    }
}