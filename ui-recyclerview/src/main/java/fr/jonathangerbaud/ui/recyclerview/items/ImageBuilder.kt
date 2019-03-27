package fr.jonathangerbaud.ui.recyclerview.items

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import fr.jonathangerbaud.core.util.ResUtils

open class ImageBuilder : WidgetBuilder()
{
    private var bitmap: Bitmap? = null
    private var drawableRes: Int? = null
    private var drawable: Drawable? = null
    private var tint: Int? = null
    private var tintRes: Int? = null
    private var scaleType: ImageView.ScaleType? = null

    override fun buildView(context: Context): View
    {
        val view = ImageView(context)
        applyViewAttributes(view)

        bitmap?.let { view.setImageBitmap(it) }
        drawableRes?.let { view.setImageResource(it) }
        drawable?.let { view.setImageDrawable(it) }
        tint?.let { view.imageTintList = ColorStateList.valueOf(it) }
        tint?.let { view.imageTintList = ColorStateList.valueOf(ResUtils.getColor(it)) }
        scaleType?.let { view.scaleType = scaleType }

        return view
    }

    fun bitmap(bitmap: Bitmap): ImageBuilder
    {
        this.bitmap = bitmap
        return this
    }

    fun drawable(@DrawableRes drawableRes: Int): ImageBuilder
    {
        this.drawableRes = drawableRes
        return this
    }

    fun drawable(drawable: Drawable): ImageBuilder
    {
        this.drawable = drawable
        return this
    }

    fun tint(@ColorInt color: Int): ImageBuilder
    {
        this.tint = color
        return this
    }

    fun tintRes(@ColorRes color: Int): ImageBuilder
    {
        this.tintRes = color
        return this
    }

    fun scaleType(scaleType: ImageView.ScaleType): ImageBuilder
    {
        this.scaleType = scaleType
        return this
    }
}
