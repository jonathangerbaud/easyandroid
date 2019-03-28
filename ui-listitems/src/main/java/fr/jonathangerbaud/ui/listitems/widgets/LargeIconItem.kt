package fr.jonathangerbaud.ui.listitems.widgets

import fr.jonathangerbaud.ui.listitems.DefaultRowItemSpec
import fr.jonathangerbaud.ui.listitems.RowItem
import fr.jonathangerbaud.ui.listitems.RowItemSpec
import fr.jonathangerbaud.ui.widgets.ImageBuilder

class LargeIconItem : ImageBuilder<LargeIconItem>(), RowItem
{
    override fun getRowItemSpecs(): RowItemSpec
    {
        return CustomRowItemSpec()
    }

    private class CustomRowItemSpec : DefaultRowItemSpec()
    {
        override fun getMinListItemHeight():Int
        {
            return SIZE_72
        }

        override fun getTopPadding(minHeight: Int): Int {
            if (minHeight <= SIZE_88)
                return SIZE_8
            else
                return SIZE_16
        }

        override fun getWidth(): Int
        {
            return SIZE_56
        }

        override fun getHeight(): Int
        {
            return SIZE_56
        }
    }
}