package fr.jonathangerbaud.ui.listitems.widgets

import fr.jonathangerbaud.ui.listitems.RowItemSpec
import fr.jonathangerbaud.ui.listitems.style.MaterialListTitleStyle

class TitleItem : TextItem()
{
    init
    {
        styler(MaterialListTitleStyle())
    }

    override fun getRowItemSpecs(): RowItemSpec
    {
        return TitleRowItemSpec()
    }

    protected class TitleRowItemSpec : CustomRowItemSpec()
    {
        override fun getTextBaseline(minHeight: Int, position: Int, count: Int): Int
        {
            // up to 64 should have only one text
            if (minHeight < SIZE_56)
                return SIZE_28
            else if (minHeight < SIZE_64)
                return SIZE_32
            else if (minHeight < SIZE_72)
                return if (position == 0) SIZE_28 else SIZE_20
            else
                return if (position == 0) SIZE_32 else SIZE_20
        }
    }
}