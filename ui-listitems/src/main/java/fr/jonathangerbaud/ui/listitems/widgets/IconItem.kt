package fr.jonathangerbaud.ui.listitems.widgets

import fr.jonathangerbaud.ui.listitems.DefaultRowItemSpec
import fr.jonathangerbaud.ui.listitems.RowItem
import fr.jonathangerbaud.ui.listitems.RowItemSpec
import fr.jonathangerbaud.ui.widgets.ImageBuilder

class IconItem : ImageBuilder<IconItem>(), RowItem
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

        override fun getTopPadding(minHeight:Int):Int
        {
            return if (minHeight < SIZE_72) SIZE_8 else SIZE_16
        }

        override fun getWidth(): Int
        {
            return SIZE_40
        }

        override fun getHeight(): Int
        {
            return SIZE_40
        }
    }
}