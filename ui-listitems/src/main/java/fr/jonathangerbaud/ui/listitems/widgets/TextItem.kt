package fr.jonathangerbaud.ui.listitems.widgets

import fr.jonathangerbaud.ui.listitems.DefaultRowItemSpec
import fr.jonathangerbaud.ui.listitems.RowItem
import fr.jonathangerbaud.ui.listitems.RowItemSpec
import fr.jonathangerbaud.ui.widgets.TextBuilder

open class TextItem : TextBuilder<TextItem>(), RowItem
{
    override fun getRowItemSpecs(): RowItemSpec
    {
        return CustomRowItemSpec()
    }

    protected open class CustomRowItemSpec : DefaultRowItemSpec()
    {
        override fun getWidth(): Int
        {
            return SIZE_MATCH_PARENT
        }

        override fun getHeight(): Int
        {
            return SIZE_WRAP_CONTENT
        }
    }
}