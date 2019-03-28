package fr.jonathangerbaud.ui.listitems.widgets

import fr.jonathangerbaud.ui.listitems.RowItemSpec
import fr.jonathangerbaud.ui.listitems.style.MaterialListOverlineStyle

class OverlineItem : TextItem()
{
    init
    {
        styler(MaterialListOverlineStyle())
    }

    override fun getRowItemSpecs(): RowItemSpec
    {
        return OverlineRowItemSpec()
    }

    protected class OverlineRowItemSpec : CustomRowItemSpec()
    {
        override fun getTextBaseline(minHeight: Int, position: Int, count: Int): Int
        {
            if (minHeight < SIZE_88)
                return SIZE_24
            else
                return SIZE_28
        }
    }
}