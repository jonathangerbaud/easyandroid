package fr.jonathangerbaud.ui.image

import android.graphics.Path
import android.graphics.Path.FillType
import android.R.attr.y
import android.R.attr.x
import android.graphics.Point


object PathHelper
{
    fun oval(width: Float, height: Float): Path = Path().apply { addOval(0f, 0f, width, height, Path.Direction.CW) }
    fun circle(size: Float): Path = Path().apply { addOval(0f, 0f, size, size, Path.Direction.CW) }
    fun rect(width: Float, height: Float): Path = Path().apply { addRect(0f, 0f, width, height, Path.Direction.CW) }
    fun square(size: Float): Path = Path().apply { addRect(0f, 0f, size, size, Path.Direction.CW) }

    fun diamond(width: Float, height: Float): Path = Path().apply {
        moveTo(width / 2, 0f)
        lineTo(width, height / 2)
        lineTo(width / 2, height)
        lineTo(0f, height / 2)
        close()
    }

    fun star(width: Float, steps: Int = 5): Path = Path().apply {
        val hMargin = width / 9
        val vMargin = width / 3

        val half = width / 2

        moveTo(half, 0.719f * width)
        lineTo(0.7575f * width, 0.875f * width)
        lineTo(0.689f * width, 0.582f * width)
        lineTo(0.954f * width, 0.385f * width)
        lineTo(0.617f * width, 0.359f * width)
        lineTo(half, 0.083f * width)
        lineTo(0.382f * width, 0.359f * width)
        lineTo(0.083f * width, 0.385f * width)
        lineTo(0.310f * width, 0.582f * width)
        lineTo(0.2425f * width, 0.875f * width)
        close()
    }

    fun heart(width: Float, height: Float): Path = Path().apply {
        // Starting point
        moveTo(width / 2, height / 5)
        // Upper left path
        cubicTo(5 * width / 14, 0f, 0f, height / 15, width / 28, 2 * height / 5)

        // Lower left path
        cubicTo(width / 14, 2 * height / 3, 3 * width / 7, 5 * height / 6, width / 2, height)

        // Lower right path
        cubicTo(4 * width / 7, 5 * height / 6, 13 * width / 14, 2 * height / 3, 27 * width / 28, 2 * height / 5);
        // Upper right path
        cubicTo(width, height / 15, 9 * width / 14, 0f, width / 2, height / 5)
    }
}