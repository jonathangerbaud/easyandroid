package fr.jonathangerbaud.ui.widgets

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

open class ImageBuilder<T : ImageBuilder<T>> : WidgetBuilder<T>()
{
    private var bitmap: Bitmap? = null
    private var drawableRes: Int? = null
    private var drawable: Drawable? = null
    private var tint: Int? = null
    private var tintRes: Int? = null
    private var scaleType: ImageView.ScaleType? = null

    override fun createView(context: Context): View
    {
        return ImageView(context)
    }

    @Suppress("NAME_SHADOWING")
    override fun applyViewAttributes(view: View)
    {
        super.applyViewAttributes(view)
        val view = view as ImageView

        bitmap?.let { view.setImageBitmap(it) }
        drawableRes?.let { view.setImageResource(it) }
        drawable?.let { view.setImageDrawable(it) }
        tint?.let { view.imageTintList = ColorStateList.valueOf(it) }
        tintRes?.let { view.imageTintList = ColorStateList.valueOf(ResUtils.getColor(it, view.context)) }
        scaleType?.let { view.scaleType = scaleType }
    }

    fun bitmap(bitmap: Bitmap) = applySelf { this.bitmap = bitmap }

    fun drawable(@DrawableRes drawableRes: Int) = applySelf { this.drawableRes = drawableRes }

    fun drawable(drawable: Drawable) = applySelf { this.drawable = drawable }

    fun tint(@ColorInt color: Int) = applySelf { this.tint = color }

    fun tintRes(@ColorRes color: Int) = applySelf { this.tintRes = color }

    fun scaleType(scaleType: ImageView.ScaleType) = applySelf {  this.scaleType = scaleType }
}
