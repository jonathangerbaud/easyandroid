package fr.jonathangerbaud.ui.recyclerview.items

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.SwitchCompat

class SwitchBuilder : WidgetBuilder()
{
    init {
        measurements(SwitchMeasurements())
    }

    override fun buildView(context: Context): View
    {
        val view = SwitchCompat(context)
        applyViewAttributes(view)

        return view
    }

    private class SwitchMeasurements : DefaultMeasurements()
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