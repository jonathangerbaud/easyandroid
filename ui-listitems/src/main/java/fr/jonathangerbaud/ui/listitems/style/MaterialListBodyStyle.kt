package fr.jonathangerbaud.ui.listitems.style

import android.widget.TextView
import fr.jonathangerbaud.core.util.AttrUtil
import fr.jonathangerbaud.ui.core.text.MaterialTypography
import fr.jonathangerbaud.ui.core.text.TextStyler


class MaterialListBodyStyle : TextStyler
{
    override fun applyDefaultStyle(textView: TextView)
    {
        MaterialTypography.TypeScale.BODY2.apply(textView)
        textView.setTextColor(AttrUtil.getColor(textView.context, android.R.attr.textColorSecondary))
    }
}