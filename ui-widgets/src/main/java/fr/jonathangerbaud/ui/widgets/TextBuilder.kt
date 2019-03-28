package fr.jonathangerbaud.ui.widgets

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.core.text.TextStyler


open class TextBuilder<T : TextBuilder<T>> : WidgetBuilder<T>()
{
    private var styler: TextStyler? = null
    private var stringRes:Int? = null
    private var string:String? = null
    private var textColor:Int? = null
    private var textColorRes:Int? = null
    private var textSize:Float? = null
    private var textSizeRes:Int? = null
    private var font:Typeface? = null
    private var gravity:Int? = null
    private var truncate:TextUtils.TruncateAt? = null
    private var maxLines:Int? = null

    override fun buildView(context: Context): View
    {
        val view = TextView(context)
        applyViewAttributes(view)

        styler?.applyDefaultStyle(view)

        stringRes?.let { view.setText(it) }
        string?.let { view.text = it }
        textColor?.let { view.setTextColor(it) }
        textColorRes?.let { view.setTextColor(ResUtils.getColor(it)) }
        textSize?.let { view.textSize = it }
        textSizeRes?.let { view.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResUtils.getDimension(it)) }
        font?.let { view.typeface = it }
        gravity?.let { view.gravity = it }
        truncate?.let {view.ellipsize = it}
        maxLines?.let { view.maxLines = it }

        return view
    }

    fun styler(styler: TextStyler) = applySelf { this.styler = styler }

    fun text(@StringRes stringRes:Int) = applySelf { this.stringRes = stringRes }

    fun text(text:String) = applySelf { this.string = text }

    fun textColor(color:Int) = applySelf { textColor = color }
    fun textColorRes(@ColorRes color:Int) = applySelf { textColorRes = color }

    /**
     * Text size in pixels
     */
    fun textSize(size:Float) = applySelf { textSize = size }

    fun textSize(@DimenRes sizeRes:Int) = applySelf { textSizeRes = sizeRes }

    fun font(font:Typeface) = applySelf { this.font = font }

    /**
     * Align text content
     * Use <code>android.view.Gravity</code> constants
     */
    fun align(gravity: Int) = applySelf { this.gravity = gravity }

    fun ellipsize(truncate: TextUtils.TruncateAt) = applySelf { this.truncate = truncate }

    fun maxlines(count: Int) = applySelf { maxLines = count }
}