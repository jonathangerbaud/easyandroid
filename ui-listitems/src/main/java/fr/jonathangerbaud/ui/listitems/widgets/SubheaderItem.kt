package fr.jonathangerbaud.ui.listitems.widgets

import fr.jonathangerbaud.ui.listitems.RowItemSpec
import fr.jonathangerbaud.ui.listitems.style.MaterialListSubheaderStyle
import fr.jonathangerbaud.ui.listitems.style.MaterialListTitleStyle

open class SubheaderItem : TextItem()
{
    init
    {
        styler(MaterialListSubheaderStyle())
    }

    override fun getRowItemSpecs(): RowItemSpec
    {
        return SubheaderItemSpec()
    }

    open protected class SubheaderItemSpec : CustomRowItemSpec()
    {
        override fun getTextBaseline(minHeight: Int, position: Int, count: Int): Int
        {
            return SIZE_24
        }
    }
}