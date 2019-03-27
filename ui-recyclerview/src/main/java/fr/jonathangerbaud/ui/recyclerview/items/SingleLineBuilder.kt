package fr.jonathangerbaud.ui.recyclerview.items

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import fr.jonathangerbaud.ui.recyclerview.items.style.DefaultTitleStyler
import fr.jonathangerbaud.ui.recyclerview.items.style.TextStyler


class SingleLineBuilder : WidgetBuilder()
{
    private var styler: TextStyler = DefaultTitleStyler()
    private var stringRes:Int? = null
    private var string:String? = null
    private var textColor:Int? = null
    private var textSize:Float? = null
    private var font:Typeface? = null
    private var gravity:Int? = null
    private var truncate:TextUtils.TruncateAt? = null

    init {
        measurements(TextMeasurements())
    }

    private class TextMeasurements : DefaultMeasurements()
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

    override fun buildView(context: Context): View
    {
        val view = TextView(context)
        applyViewAttributes(view)

        styler.applyDefaultStyle(view)

        stringRes?.let { view.setText(it) }
        string?.let { view.text = it }
        textColor?.let { view.setTextColor(it) }
        textColor?.let { view.setTextColor(it) }
        textSize?.let { view.textSize = it }
        font?.let { view.typeface = it }
        gravity?.let { view.gravity = it }
        truncate?.let {view.ellipsize = truncate}

        return view
    }

    fun styler(styler:TextStyler): SingleLineBuilder
    {
        this.styler = styler
        return this
    }

    fun text(@StringRes stringRes:Int):SingleLineBuilder
    {
        this.stringRes = stringRes
        return this
    }

    fun text(text:String):SingleLineBuilder
    {
        this.string = text
        return this
    }

    fun textColor(color:Int): SingleLineBuilder
    {
        this.textColor = color
        return this
    }

    /**
     * Text size in pixels
     */
    fun textSize(size:Float): SingleLineBuilder
    {
        this.textSize = size
        return this
    }

    fun font(font:Typeface): SingleLineBuilder
    {
        this.font = font
        return this
    }

    /**
     * Align text content
     * Use <code>android.view.Gravity</code> constants
     */
    fun align(gravity: Int): SingleLineBuilder
    {
        this.gravity = gravity
        return this
    }

    fun ellipsize(truncate: TextUtils.TruncateAt): SingleLineBuilder
    {
        this.truncate = truncate
        return this
    }

}