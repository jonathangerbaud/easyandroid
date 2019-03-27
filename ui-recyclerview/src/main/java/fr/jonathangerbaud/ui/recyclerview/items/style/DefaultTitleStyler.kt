package fr.jonathangerbaud.ui.recyclerview.items.style

import android.util.TypedValue
import android.widget.TextView
import fr.jonathangerbaud.core.util.AttrUtil
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.recyclerview.R


class DefaultTitleStyler : TextStyler
{
    override fun applyDefaultStyle(textView: TextView)
    {
        textView.setTextColor(AttrUtil.getColor(textView.context, android.R.attr.textColorPrimary))
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResUtils.getDimension(R.dimen.md_text_subtitle1))
    }
}