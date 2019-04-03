package fr.jonathangerbaud.ui.image

import android.graphics.Paint
import android.graphics.Path
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import fr.jonathangerbaud.core.util.ResUtils


class MaskOptions private constructor(
    val path: Path,
    val borderWidth: Float?,
    val borderColor: Int?,
    val borderCap: Paint.Cap?,
    val borderJoin: Paint.Join?,
    val borderMiter: Float?
)
{
    class Builder(val path: Path)
    {
        var borderWidth: Float? = null
        var borderColor: Int? = null
        var borderCap: Paint.Cap? = null
        var borderJoin: Paint.Join? = null
        var borderMiter: Float? = null

        fun borderWidth(width: Float): Builder
        {
            borderWidth = width
            return this
        }

        fun borderColor(@ColorInt color: Int): Builder
        {
            borderColor = color
            return this
        }

        fun borderColorRes(@ColorRes colorResId: Int): Builder
        {
            borderColor = ResUtils.getColor(colorResId)
            return this
        }

        fun borderCap(cap: Paint.Cap): Builder
        {
            borderCap = cap
            return this
        }

        fun borderJoin(join: Paint.Join): Builder
        {
            borderJoin = join
            return this
        }

        fun borderMiter(miter: Float): Builder
        {
            borderMiter = miter
            return this
        }

        fun build(): MaskOptions
        {
            return MaskOptions(path, borderWidth, borderColor, borderCap, borderJoin, borderMiter)
        }
    }
}