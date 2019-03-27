package fr.jonathangerbaud.ui.recyclerview.items.style

import android.widget.TextView
import fr.jonathangerbaud.core.util.AttrUtil
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.recyclerview.R


class DefaultTitleStyler : TextStyler
{
    override fun applyDefaultStyle(textView: TextView)
    {
        textView.setTextColor(AttrUtil.getColor(textView.context, android.R.attr.textColorPrimary))
        textView.textSize = ResUtils.getDimension(R.dimen.md_text_subtitle1)
    }
}