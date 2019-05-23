package fr.jonathangerbaud.ui.core.ext

import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.core.text.TextStyler

fun TextView.setTextOrHide(text: String?)
{
    if (text.isNullOrBlank())
    {
        hide()
    }
    else
    {
        this.text = text
        show()
    }
}

fun TextView.styler(styler:TextStyler) = styler.applyDefaultStyle(this)
fun TextView.setTextColorRes(@ColorRes colorRes:Int) = setTextColor(ResUtils.getColor(colorRes, context))
fun TextView.setTextSizePx(sizeInPx:Float) = setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeInPx)
fun TextView.setTextSizeRes(@DimenRes dimenRes:Int) { textSize = ResUtils.getDimensionPxSize(context, dimenRes).toFloat() }

