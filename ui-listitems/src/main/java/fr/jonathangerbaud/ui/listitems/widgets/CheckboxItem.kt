package fr.jonathangerbaud.ui.listitems.widgets

import android.view.Gravity
import fr.jonathangerbaud.ui.listitems.DefaultRowItemSpec
import fr.jonathangerbaud.ui.listitems.RowItem
import fr.jonathangerbaud.ui.listitems.RowItemSpec
import fr.jonathangerbaud.ui.widgets.CheckboxBuilder

class CheckboxItem : CheckboxBuilder<CheckboxItem>(), RowItem
{
    override fun getRowItemSpecs(): RowItemSpec
    {
        return CustomRowItemSpec()
    }

    private class CustomRowItemSpec : DefaultRowItemSpec()
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