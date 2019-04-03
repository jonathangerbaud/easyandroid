package fr.jonathangerbaud.ui.core.view

import fr.jonathangerbaud.ui.core.AspectRatio

class RatioDelegate
{
    var ratio:Float = -1f

    fun getWidthSpec(widthMeasureSpec:Int, heightMeasureSpec:Int): Int
    {
        return if (ratio == AspectRatio.NONE || ratio >= AspectRatio.SQUARE)
            widthMeasureSpec
        else
            heightMeasureSpec
    }

    fun getHeightSpec(widthMeasureSpec:Int, heightMeasureSpec:Int): Int
    {
        return if (ratio == AspectRatio.NONE || ratio < AspectRatio.SQUARE)
            heightMeasureSpec
        else
            widthMeasureSpec
    }

    fun getWidthDimension(measuredWidth:Int, measuredHeight:Int): Int
    {
        return if (ratio == AspectRatio.NONE || ratio >= AspectRatio.SQUARE)
            measuredWidth
        else
            (measuredHeight * ratio).toInt()
    }

    fun getHeightDimension(measuredWidth:Int, measuredHeight:Int): Int
    {
        return if (ratio == AspectRatio.NONE || ratio < AspectRatio.SQUARE)
            measuredHeight
        else
            (measuredWidth / ratio).toInt()
    }
}
