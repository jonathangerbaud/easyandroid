package fr.jonathangerbaud.ui.listitems.widgets

import fr.jonathangerbaud.ui.listitems.DefaultRowItemSpec
import fr.jonathangerbaud.ui.listitems.RowItem
import fr.jonathangerbaud.ui.listitems.RowItemSpec
import fr.jonathangerbaud.ui.widgets.ImageBuilder

class SmallIconItem : ImageBuilder<SmallIconItem>(), RowItem
{
    override fun getRowItemSpecs(): RowItemSpec
    {
        return CustomRowItemSpec()
    }

    private class CustomRowItemSpec : DefaultRowItemSpec()
    {
        override fun getMinListItemHeight():Int
        {
            return SIZE_48
        }

        override fun getTopPadding(minHeight: Int): Int {
            if (minHeight >= SIZE_56)
                return SIZE_16
            else
                return SIZE_12
        }

        override fun getEndMargin(): Int {
            return SIZE_32
        }

        override fun getWidth(): Int
        {
            return SIZE_24
        }

        override fun getHeight(): Int
        {
            return SIZE_24
        }
    }
}