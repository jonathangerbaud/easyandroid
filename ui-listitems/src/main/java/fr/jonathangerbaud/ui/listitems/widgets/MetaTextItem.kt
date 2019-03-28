package fr.jonathangerbaud.ui.listitems.widgets

import fr.jonathangerbaud.ui.core.text.MaterialTypography
import fr.jonathangerbaud.ui.listitems.RowItemSpec
import fr.jonathangerbaud.ui.listitems.style.MaterialListTitleStyle

class MetaTextItem : TextItem()
{
    init
    {
        styler(MaterialListTitleStyle())
        textSize(MaterialTypography.TypeScale.SUBTITLE2.size)
    }

    override fun getRowItemSpecs(): RowItemSpec
    {
        return MetaRowItemSpec()
    }

    protected class MetaRowItemSpec : CustomRowItemSpec()
    {
        override fun getTextBaseline(minHeight: Int, position: Int, count: Int): Int
        {
            return SIZE_28
        }
    }
}