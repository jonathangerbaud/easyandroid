package fr.jonathangerbaud.ui.recyclerview.items

import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes


abstract class WidgetBuilder
{
    var measurements: Measurements = DefaultMeasurements()

    protected var alpha: Float? = null
    protected var foregroundDrawable: Drawable? = null
    protected var backgroundDrawable: Drawable? = null
    protected var backgroundRes: Int? = null
    protected var backgroundColor: Int? = null
    protected var enabled: Boolean? = null
    protected var visibility: Int = View.VISIBLE

    abstract fun buildView(context: Context): View

    fun measurements(measurements: Measurements)
    {
        this.measurements = measurements
    }

    fun alpha(alpha: Float): WidgetBuilder
    {
        this.alpha = alpha
        return this
    }

    @TargetApi(23)
    fun foreground(drawable: Drawable): WidgetBuilder
    {
        this.foregroundDrawable = drawable
        return this
    }

    fun background(drawable: Drawable): WidgetBuilder
    {
        this.backgroundDrawable = drawable
        return this
    }

    fun background(@ColorRes color: Int): WidgetBuilder
    {
        this.backgroundColor = color
        return this
    }

    fun backgroundRes(@DrawableRes drawable: Int): WidgetBuilder
    {
        this.backgroundRes = drawable
        return this
    }

    fun enabled(enabled: Boolean): WidgetBuilder
    {
        this.enabled = enabled
        return this
    }

    fun invisible(): WidgetBuilder
    {
        this.visibility = View.INVISIBLE
        return this
    }

    fun gone(): WidgetBuilder
    {
        this.visibility = View.GONE
        return this
    }

    protected fun applyViewAttributes(view: View)
    {
        alpha?.let { view.alpha = it }
        foregroundDrawable?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
               view.foreground = it
        }
        backgroundDrawable?.let { view.background = it }
        backgroundColor?.let { view.setBackgroundColor(it) }
        backgroundRes?.let { view.setBackgroundResource(it) }
        enabled?.let { view.isEnabled = it }
        view.visibility = visibility
    }
}