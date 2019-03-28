package fr.jonathangerbaud.ui.listitems.style

import android.widget.TextView
import fr.jonathangerbaud.core.util.AttrUtil
import fr.jonathangerbaud.ui.core.text.MaterialTypography
import fr.jonathangerbaud.ui.core.text.TextStyler


class MaterialListHeadlineStyle : TextStyler
{
    override fun applyDefaultStyle(textView: TextView)
    {
        MaterialTypography.TypeScale.H5.apply(textView)
        textView.setTextColor(AttrUtil.getColor(textView.context, android.R.attr.textColorPrimary))
    }
}