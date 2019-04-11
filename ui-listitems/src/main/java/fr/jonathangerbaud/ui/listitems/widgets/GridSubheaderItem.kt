package fr.jonathangerbaud.ui.listitems.widgets

import fr.jonathangerbaud.ui.listitems.RowItemSpec
import fr.jonathangerbaud.ui.listitems.style.MaterialListSubheaderStyle
import fr.jonathangerbaud.ui.listitems.style.MaterialListTitleStyle

open class GridSubheaderItem : TextItem()
{
    init
    {
        styler(MaterialListSubheaderStyle())
    }

    override fun getRowItemSpecs(): RowItemSpec
    {
        return GridSubheaderItemSpec()
    }

    protected open class GridSubheaderItemSpec : CustomRowItemSpec()
    {
        override fun getStartMarginIfStartComponent(): Int
        {
            return 0
        }

        override fun getEndMarginIfEndComponent(): Int
        {
            return 0
        }

        override fun getTextBaseline(minHeight: Int, position: Int, count: Int): Int
        {
            return SIZE_32
        }
    }
}