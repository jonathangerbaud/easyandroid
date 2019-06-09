package fr.jonathangerbaud.ui.appcompat

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import fr.jonathangerbaud.core.AppInstance

object BitmapUtil
{
    fun vectorToBitmap(@DrawableRes drawableRes: Int, @ColorRes tintColor: Int? = null): Bitmap?
    {
        // retrieve the actual drawable
        val drawable = ContextCompat.getDrawable(AppInstance.get(), drawableRes) ?: return null
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

        // add the tint if it exists
        tintColor?.let {
            DrawableCompat.setTint(drawable, ContextCompat.getColor(AppInstance.get(), it))
        }

        // draw it onto the bitmap
        val canvas = Canvas(bitmap)
        drawable.draw(canvas)
        return bitmap
    }
}