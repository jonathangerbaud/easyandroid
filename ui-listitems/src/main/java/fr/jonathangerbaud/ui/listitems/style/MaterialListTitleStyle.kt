package fr.jonathangerbaud.ui.listitems.style

import android.widget.TextView
import fr.jonathangerbaud.core.util.AttrUtil
import fr.jonathangerbaud.ui.core.text.MaterialTypography
import fr.jonathangerbaud.ui.core.text.TextStyler


class MaterialListTitleStyle : TextStyler
{
    override fun applyDefaultStyle(textView: TextView)
    {
        MaterialTypography.TypeScale.SUBTITLE1.apply(textView)
        textView.setTextColor(AttrUtil.getColor(textView.context, android.R.attr.textColorPrimary))
    }
}