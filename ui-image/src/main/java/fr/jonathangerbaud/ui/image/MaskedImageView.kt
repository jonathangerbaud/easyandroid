package fr.jonathangerbaud.ui.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import fr.jonathangerbaud.core.util.ResUtils

class MaskedImageView : ImageView
{
    // Nullable type since it's initialized after super constructor
    // which might call setImageDrawable and crash on null reference
    private var maskDelegate: MaskDelegate? = MaskDelegate(this)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        maskDelegate?.onImageDrawableReset(drawable)
    }

    override fun setImageBitmap(bm: Bitmap)
    {
        super.setImageBitmap(bm)
        maskDelegate?.onImageDrawableReset(drawable)
    }

    override fun setImageDrawable(drawable: Drawable?)
    {
        super.setImageDrawable(drawable)
        maskDelegate?.onImageDrawableReset(getDrawable())
    }

    override fun setImageResource(resId: Int)
    {
        super.setImageResource(resId)
        maskDelegate?.onImageDrawableReset(drawable)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
    {
        super.onSizeChanged(w, h, oldw, oldh)
        maskDelegate?.onSizeChanged(w, h)
    }

    override fun onDraw(canvas: Canvas)
    {
        if (!maskDelegate!!.onDraw(canvas))
            super.onDraw(canvas)
    }

    fun setMaskPath(path: Path)
    {
        maskDelegate?.setPath(path)
    }

    fun setMaskBorderWidth(width:Float)
    {
        maskDelegate?.borderWidth = width
    }

    fun setMaskBorderColor(@ColorInt color:Int)
    {
        maskDelegate?.borderColor = color
    }

    fun setMaskBorderColorRes(@ColorRes colorResId:Int)
    {
        maskDelegate?.borderColor = ResUtils.getColor(colorResId)
    }

    fun setMaskBorderCap(cap: Paint.Cap)
    {
        maskDelegate?.borderCap = cap
    }

    fun setMaskBorderJoin(join: Paint.Join)
    {
        maskDelegate?.borderJoin = join
    }

    fun setMaskBorderMiter(miter: Float)
    {
        maskDelegate?.borderMiter = miter
    }
}