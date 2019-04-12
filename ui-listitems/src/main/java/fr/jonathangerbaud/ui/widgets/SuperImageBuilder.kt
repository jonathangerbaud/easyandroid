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
import fr.jonathangerbaud.ui.image.MaskOptions
import fr.jonathangerbaud.ui.image.SuperImageView

open class SuperImageBuilder<T : SuperImageBuilder<T>> : ImageBuilder<T>()
{
    private var aspectRatio:Float? = null
    private var maskOptions: MaskOptions? = null

    override fun createView(context: Context): View
    {
        return SuperImageView(context)
    }

    @Suppress("NAME_SHADOWING")
    override fun applyViewAttributes(view: View)
    {
        super.applyViewAttributes(view)
        val view = view as SuperImageView

        aspectRatio?.let { view.setAspectRatio(it) }
        maskOptions?.let { view.setMaskOptions(it) }
    }

    fun aspectRatio(aspectRatio:Float)
    {
        this.aspectRatio = aspectRatio
    }

    fun maskOptions(options:MaskOptions)
    {
        this.maskOptions = options
    }
}