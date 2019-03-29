package fr.jonathangerbaud.ui.core.text

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import fr.jonathangerbaud.core.ext.d
import fr.jonathangerbaud.core.util.AndroidUtil
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.core.R

open class TextAppearance
{
    var size: Int? = null
    var font: Typeface? = null
    var letterSpacing: Float? = null
    var fallbackLineSpacing: Boolean? = null
    var case: MaterialTypography.Case.TextCase? = null

    @ColorInt
    var color: Int? = null
        set(value)
        {
            colorStateList = if (value != null) ColorStateList.valueOf(value) else null
        }
    var colorStateList: ColorStateList? = null

    @ColorInt
    var hintColor: Int? = null
        set(value)
        {
            hintColorStateList = if (value != null) ColorStateList.valueOf(value) else null
        }
    var hintColorStateList: ColorStateList? = null

    @ColorInt
    var highlightColor: Int? = null

    @ColorInt
    var linkColor: Int? = null
        set(value)
        {
            linkColorStateList = if (value != null) ColorStateList.valueOf(value) else null
        }
    var linkColorStateList: ColorStateList? = null

    var elegantTextHeight: Boolean? = null
    var shadowColor: Int? = null
    var shadowDx: Float = 0f
    var shadowDy: Float = 0f
    var shadowRadius: Float = 0f

    var fontFeatureSettings: String? = null

    private var typefaceIndex: Int = -1
    private var fontFamily: String? = null
    private var fontFamilyExplicit: Boolean = false
    private var styleIndex: Int = -1
    private var fontWeight: Int = -1

    fun sizeSp(sizeInSp: Int)
    {
        size = ResUtils.getSpInPx(sizeInSp)
    }

    fun apply(textView: TextView)
    {
        size?.let { textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.toFloat()) }
        letterSpacing?.let { textView.letterSpacing = it }
        case?.let { textView.transformationMethod = it }
        colorStateList?.let { textView.setTextColor(it) }
        hintColorStateList?.let { textView.setHintTextColor(it) }
        linkColorStateList?.let { textView.setLinkTextColor(it) }
        highlightColor?.let { textView.highlightColor = it }
        elegantTextHeight?.let { textView.isElegantTextHeight = it }
        shadowColor?.let { textView.setShadowLayer(shadowRadius, shadowDx, shadowDy, it) }

        fontFeatureSettings?.let { textView.fontFeatureSettings = fontFeatureSettings }

        if (AndroidUtil.isMinPie())
            fallbackLineSpacing?.let { textView.isFallbackLineSpacing = it }

        if (font == null && fontFamily != null)
        {
            // Lookup normal Typeface from system font map.
            val normalTypeface = Typeface.create(fontFamily, Typeface.NORMAL)
            resolveStyleAndSetTypeface(textView, normalTypeface, styleIndex, fontWeight)
        }
        else if (font != null)
        {
            resolveStyleAndSetTypeface(textView, font, styleIndex, fontWeight)
        }
        else
        {  // both typeface and fontFamily is null.
            when (typefaceIndex)
            {
                SANS -> resolveStyleAndSetTypeface(textView, Typeface.SANS_SERIF, styleIndex, fontWeight)
                SERIF -> resolveStyleAndSetTypeface(textView, Typeface.SERIF, styleIndex, fontWeight)
                MONOSPACE -> resolveStyleAndSetTypeface(textView, Typeface.MONOSPACE, styleIndex, fontWeight)
                DEFAULT_TYPEFACE -> resolveStyleAndSetTypeface(textView, null, styleIndex, fontWeight)
                else -> resolveStyleAndSetTypeface(textView, null, styleIndex, fontWeight)
            }
        }
    }

    @Suppress("NAME_SHADOWING")
    private fun resolveStyleAndSetTypeface(textView:TextView, typeface: Typeface?, style: Int, weight: Int)
    {
        return if (weight >= 0 && AndroidUtil.isMinPie())
        {
            val weight = Math.min(1000, weight)
            val italic = style and Typeface.ITALIC != 0
            textView.setTypeface(Typeface.create(typeface, weight, italic))
        }
        else
        {
            textView.setTypeface(typeface, style)
        }
    }

    companion object
    {
        private const val DEFAULT_TYPEFACE: Int = -1
        private const val SANS: Int = 0
        private const val SERIF: Int = 1
        private const val MONOSPACE: Int = 2

        fun fromStyle(context: Context, @StyleRes style: Int): TextAppearance
        {
            val appearance: TypedArray? = context.obtainStyledAttributes(style, R.styleable.EasyAndroidTextAppearance)

            return readTextAppearance(context, appearance)
        }

        private fun readTextAppearance(context: Context, appearance: TypedArray?): TextAppearance
        {
            val ta = TextAppearance()

            if (appearance == null)
                return ta

            val n = appearance.indexCount
            for (i in 0 until n)
            {
                val attr = appearance.getIndex(i)
                d("$i $attr")
                when (attr)
                {
                    R.styleable.EasyAndroidTextAppearance_android_textColorHighlight ->
                        ta.highlightColor = appearance.getColor(attr, 0)
                    R.styleable.EasyAndroidTextAppearance_android_textColor ->
                        ta.colorStateList = appearance.getColorStateList(attr)
                    R.styleable.EasyAndroidTextAppearance_android_textColorHint ->
                        ta.hintColorStateList = appearance.getColorStateList(attr)
                    R.styleable.EasyAndroidTextAppearance_android_textColorLink ->
                        ta.linkColorStateList = appearance.getColorStateList(attr)
                    R.styleable.EasyAndroidTextAppearance_android_textSize ->
                        ta.size = appearance.getDimensionPixelSize(attr, 0)
                    R.styleable.EasyAndroidTextAppearance_android_textAllCaps ->
                        if (appearance.getBoolean(attr, false)) ta.case = MaterialTypography.Case.UPPER_CASE
                    R.styleable.EasyAndroidTextAppearance_android_shadowColor ->
                        ta.shadowColor = appearance.getInt(attr, 0)
                    R.styleable.EasyAndroidTextAppearance_android_shadowDx ->
                        ta.shadowDx = appearance.getFloat(attr, 0f)
                    R.styleable.EasyAndroidTextAppearance_android_shadowDy ->
                        ta.shadowDy = appearance.getFloat(attr, 0f)
                    R.styleable.EasyAndroidTextAppearance_android_shadowRadius ->
                        ta.shadowRadius = appearance.getFloat(attr, 0f)
                    R.styleable.EasyAndroidTextAppearance_android_elegantTextHeight ->
                        ta.elegantTextHeight = appearance.getBoolean(attr, false)
                    R.styleable.EasyAndroidTextAppearance_android_fallbackLineSpacing ->
                        ta.fallbackLineSpacing = appearance.getBoolean(attr, false)
                    R.styleable.EasyAndroidTextAppearance_android_letterSpacing ->
                        ta.letterSpacing = appearance.getFloat(attr, 0f)
                    R.styleable.EasyAndroidTextAppearance_android_typeface ->
                    {
                        ta.typefaceIndex = appearance.getInt(attr, -1)
                        if (ta.typefaceIndex != -1 && !ta.fontFamilyExplicit)
                            ta.fontFamily = null
                    }
                    R.styleable.EasyAndroidTextAppearance_android_fontFamily ->
                    {
                        if (!context.isRestricted/* && context.canLoadUnsafeResources()*/)
                        {
                            if (AndroidUtil.isMinOreo())
                            {
                                try
                                {
                                    ta.font = appearance.getFont(attr)
                                }
                                catch (e: UnsupportedOperationException)
                                {
                                    // Expected if it is not a font resource.
                                }
                                catch (e: Resources.NotFoundException)
                                {
                                }
                            }

                        }
                        if (ta.font == null)
                            ta.fontFamily = appearance.getString(attr)

                        ta.fontFamilyExplicit = true
                    }

                    R.styleable.EasyAndroidTextAppearance_android_textStyle ->
                        ta.styleIndex = appearance.getInt(attr, -1)
                    R.styleable.EasyAndroidTextAppearance_android_textFontWeight ->
                        ta.fontWeight = appearance.getInt(attr, -1)
                    R.styleable.EasyAndroidTextAppearance_android_fontFeatureSettings ->
                        ta.fontFeatureSettings = appearance.getString(attr)
                }
            }

            if (ta.typefaceIndex != -1 && !ta.fontFamilyExplicit)
                ta.fontFamily = null

            return ta
        }
    }
}