package fr.jonathangerbaud.ui.core.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView


class RatioImageView : ImageView
{
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val ratioDelegate:RatioDelegate = RatioDelegate()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
    {
        super.onMeasure(ratioDelegate.getWidthSpec(widthMeasureSpec, heightMeasureSpec),
            ratioDelegate.getHeightSpec(widthMeasureSpec, heightMeasureSpec))

        setMeasuredDimension(ratioDelegate.getWidthDimension(measuredWidth, measuredHeight),
            ratioDelegate.getHeightDimension(measuredWidth, measuredHeight))
    }
}