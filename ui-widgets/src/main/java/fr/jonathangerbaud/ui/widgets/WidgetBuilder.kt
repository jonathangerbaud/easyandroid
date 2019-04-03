package fr.jonathangerbaud.ui.widgets

import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes


abstract class WidgetBuilder<T : WidgetBuilder<T>> : ViewBuilder
{
    protected var alpha: Float? = null
    protected var foregroundDrawable: Drawable? = null
    protected var backgroundDrawable: Drawable? = null
    protected var backgroundRes: Int? = null
    protected var backgroundColor: Int? = null
    protected var enabled: Boolean? = null
    protected var visibility: Int = View.VISIBLE

    @Suppress("UNCHECKED_CAST")
    protected fun self(): T = this as T

    protected fun applySelf(block: T.() -> Unit): T =
        self().apply { block() }

    fun alpha(alpha: Float) = applySelf { this.alpha = alpha }

    @TargetApi(23)
    fun foreground(drawable: Drawable) = applySelf { foregroundDrawable = drawable }

    fun background(drawable: Drawable) = applySelf { backgroundDrawable = drawable }
    fun background(@ColorRes color: Int) = applySelf { backgroundColor = color }
    fun backgroundRes(@DrawableRes drawable: Int) = applySelf { backgroundRes = drawable }

    fun enabled(enabled: Boolean) = applySelf { this.enabled = enabled }

    fun invisible() = applySelf { this.visibility = View.INVISIBLE }

    fun gone() = applySelf {         this.visibility = View.GONE }

    final override fun build(context: Context): View
    {
        val view = createView(context)
        applyViewAttributes(view)
        return view
    }

    protected abstract fun createView(context:Context): View

    protected open fun applyViewAttributes(view: View)
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