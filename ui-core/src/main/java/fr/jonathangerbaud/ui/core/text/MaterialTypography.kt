package fr.jonathangerbaud.ui.core.text

import android.graphics.Rect
import android.graphics.Typeface
import android.text.method.TransformationMethod
import android.view.View
import android.widget.TextView

class MaterialTypography
{
    /**
     * @param size text size in scaled pixels (sp)
     */
    class Style(
        val size: Float,
        val font: Typeface,
        val letterSpacing: Float,
        val case: Case.TextCase? = Case.CAPITALIZE_FIRST
    )
    {
        fun apply(view: TextView)
        {
            view.textSize = size
            view.typeface = font
            view.letterSpacing = letterSpacing
            view.transformationMethod = case
        }
    }

    class TypeScale
    {
        companion object
        {
            val H1 = Style(96f, Font.ROBOTO_LIGHT, -1.0156f)
            val H2 = Style(60f, Font.ROBOTO_LIGHT, -0.0083f)
            val H3 = Style(48f, Font.ROBOTO_REGULAR, 0f)
            val H4 = Style(34f, Font.ROBOTO_REGULAR, 0.0073f)
            val H5 = Style(24f, Font.ROBOTO_REGULAR, 0f)
            val H6 = Style(20f, Font.ROBOTO_MEDIUM, 0.0125f)
            val SUBTITLE1 = Style(16f, Font.ROBOTO_REGULAR, 0.0093f)
            val SUBTITLE2 = Style(14f, Font.ROBOTO_MEDIUM, 0.0071f)
            val BODY1 = Style(16f, Font.ROBOTO_REGULAR, 0.0312f)
            val BODY2 = Style(14f, Font.ROBOTO_REGULAR, 0.0178f)
            val BUTTON = Style(14f, Font.ROBOTO_REGULAR, 0.089f, Case.UPPER_CASE)
            val CAPTION = Style(12f, Font.ROBOTO_REGULAR, 0.033f)
            val OVERLINE = Style(10f, Font.ROBOTO_REGULAR, 0.16f, Case.UPPER_CASE)
        }
    }

    class Case
    {
        abstract class TextCase : TransformationMethod
        {
            override fun onFocusChanged(
                view: View?, sourceText: CharSequence?, focused: Boolean, direction: Int,
                previouslyFocusedRect: Rect?
            )
            {
            }
        }

        class UpperCase : TextCase()
        {
            override fun getTransformation(source: CharSequence?, view: View?): CharSequence?
            {
                return source?.toString()?.toUpperCase()
            }
        }

        class LowerCase : TextCase()
        {
            override fun getTransformation(source: CharSequence?, view: View?): CharSequence?
            {
                return source?.toString()?.toLowerCase()
            }
        }

        class CapitalizeFirst : TextCase()
        {
            override fun getTransformation(source: CharSequence?, view: View?): CharSequence?
            {
                return source?.toString()?.capitalize()
            }
        }

        /*class CapitalizeSentence : TextCase()
        {
            override fun getTransformation(source: CharSequence?, view: View?): CharSequence?
            {
                return source?.toString()?.toUpperCase()
            }
        }*/

        class CapitalizeWord : TextCase()
        {
            override fun getTransformation(source: CharSequence?, view: View?): CharSequence?
            {
                source?.let {
                    val chunks = source.split(" ")
                    return chunks.map { it.capitalize() }.joinToString { " " }
                }
                return null
            }
        }

        companion object
        {
            val NONE = null
            val UPPER_CASE = UpperCase()
            val LOWER_CASE = LowerCase()
            val CAPITALIZE_FIRST = CapitalizeFirst()
            val CAPITALIZE_WORD = CapitalizeWord()
        }
    }

    class Font
    {
        companion object
        {
            val ROBOTO_THIN = Typeface.create("sans-serif-thin", Typeface.NORMAL)
            val ROBOTO_THIN_ITALIC = Typeface.create("sans-serif-thin", Typeface.ITALIC)
            val ROBOTO_LIGHT = Typeface.create("sans-serif-light", Typeface.NORMAL)
            val ROBOTO_LIGHT_ITALIC = Typeface.create("sans-serif-light", Typeface.ITALIC)
            val ROBOTO_REGULAR = Typeface.create("sans-serif", Typeface.NORMAL)
            val ROBOTO_REGULAR_ITALIC = Typeface.create("sans-serif", Typeface.ITALIC)
            val ROBOTO_SMALLCAPS = Typeface.create("sans-serif-smallcaps", Typeface.NORMAL)
            val ROBOTO_SMALLCAPS_ITALIC = Typeface.create("sans-serif-smallcaps", Typeface.ITALIC)
            val ROBOTO_BOLD = Typeface.create("sans-serif", Typeface.BOLD)
            val ROBOTO_BOLD_ITALIC = Typeface.create("sans-serif", Typeface.ITALIC)
            val ROBOTO_CONDENSED_LIGHT = Typeface.create("sans-serif-condensed-light", Typeface.NORMAL)
            val ROBOTO_CONDENSED_LIGHT_ITALIC = Typeface.create("sans-serif-condensed-light", Typeface.ITALIC)
            val ROBOTO_CONDENSED = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
            val ROBOTO_CONDENSED_BOLD = Typeface.create("sans-serif-condensed-medium", Typeface.BOLD)
            val ROBOTO_CONDENSED_ITALIC = Typeface.create("sans-serif-condensed-medium", Typeface.ITALIC)
            val ROBOTO_CONDENSED_MEDIUM = Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL)
            val ROBOTO_CONDENSED_MEDIUM_ITALIC = Typeface.create("sans-serif-condensed-medium", Typeface.ITALIC)
            val ROBOTO_MEDIUM = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            val ROBOTO_MEDIUM_ITALIC = Typeface.create("sans-serif-medium", Typeface.ITALIC)
            val ROBOTO_BLACK = Typeface.create("sans-serif-black", Typeface.NORMAL)
            val ROBOTO_BLACK_ITALIC = Typeface.create("sans-serif-black", Typeface.ITALIC)
        }
    }
}