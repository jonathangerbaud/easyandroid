package fr.jonathangerbaud.ui.listitems.style

import android.widget.TextView
import fr.jonathangerbaud.core.util.AttrUtil
import fr.jonathangerbaud.ui.core.text.MaterialTypography
import fr.jonathangerbaud.ui.core.text.TextStyler


class MaterialListCaptionStyle : TextStyler
{
    override fun applyDefaultStyle(textView: TextView)
    {
        MaterialTypography.TypeScale.CAPTION.apply(textView)
        textView.setTextColor(AttrUtil.getColor(textView.context, android.R.attr.textColorSecondary))
    }
}