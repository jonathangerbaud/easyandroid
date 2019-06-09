package fr.jonathangerbaud.ui.core

import android.graphics.Color

object ColorUtil
{
    private fun interpolatedRGBColorTo(from: Int, end: Int, fraction: Double): Int
    {
        val f = Math.min(1.0, Math.max(0.0, fraction))

        val r1 = Color.red(from)
        val g1 = Color.green(from)
        val b1 = Color.blue(from)
        val a1 = Color.alpha(from)

        val r2 = Color.red(end)
        val g2 = Color.green(end)
        val b2 = Color.blue(end)
        val a2 = Color.alpha(end)

        val r: Int = (r1 + (r2 - r1) * f).toInt()
        val g: Int = (g1 + (g2 - g1) * f).toInt()
        val b: Int = (b1 + (b2 - b1) * f).toInt()
        val a: Int = (a1 + (a2 - a1) * f).toInt()

        return setAlphaComponent(Color.rgb(r, g, b), a)
    }

    private fun setAlphaComponent(color: Int, alpha: Int): Int
    {
        if (alpha < 0 || alpha > 255)
        {
            throw IllegalArgumentException("alpha must be between 0 and 255.")
        }
        return color and 0x00ffffff or (alpha shl 24)
    }

    fun lighten(color: Int, fraction: Double): Int
    {
        var red = Color.red(color)
        var green = Color.green(color)
        var blue = Color.blue(color)
        val alpha = Color.alpha(color)

        red = lightenColor(red, fraction)
        green = lightenColor(green, fraction)
        blue = lightenColor(blue, fraction)

        return Color.argb(alpha, red, green, blue)
    }

    fun darken(color: Int, fraction: Double): Int
    {
        var red = Color.red(color)
        var green = Color.green(color)
        var blue = Color.blue(color)
        val alpha = Color.alpha(color)

        red = darkenColor(red, fraction)
        green = darkenColor(green, fraction)
        blue = darkenColor(blue, fraction)

        return Color.argb(alpha, red, green, blue)
    }

    private fun darkenColor(color: Int, fraction: Double): Int
    {
        return Math.max(color - color * fraction, 0.0).toInt()
    }

    private fun lightenColor(color: Int, fraction: Double): Int
    {
        return Math.min(color + color * fraction, 255.0).toInt()
    }

    fun isColorDark(color: Int): Boolean
    {
        val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255

        return darkness > 0.5
    }
}