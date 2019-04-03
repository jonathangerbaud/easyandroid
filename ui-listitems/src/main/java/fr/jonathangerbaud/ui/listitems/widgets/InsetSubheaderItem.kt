package fr.jonathangerbaud.ui.listitems.widgets

import fr.jonathangerbaud.ui.listitems.RowItemSpec
import fr.jonathangerbaud.ui.listitems.style.MaterialListSubheaderStyle
import fr.jonathangerbaud.ui.listitems.style.MaterialListTitleStyle

open class InsetSubheaderItem : SubheaderItem()
{
    override fun getRowItemSpecs(): RowItemSpec
    {
        return InsetSubheaderItemSpec()
    }

    private class InsetSubheaderItemSpec : SubheaderItemSpec()
    {
        override fun getStartMarginIfStartComponent(): Int
        {
            return SIZE_72
        }
    }
}