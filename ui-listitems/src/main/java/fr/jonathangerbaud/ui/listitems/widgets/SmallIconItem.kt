package fr.jonathangerbaud.ui.listitems.widgets

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import fr.jonathangerbaud.ui.image.SuperImageView


open class SmallIconItem(initView: SuperImageView.() -> Unit = {}) : IconItem(initView)
{
    constructor(@DrawableRes drawableRes: Int, initView: SuperImageView.() -> Unit = {}) : this({
        this.setImageResource(drawableRes)
        initView(this)
    })

    constructor(
        drawable: Drawable,
        initView: SuperImageView.() -> Unit = {}
    ) : this({
        this.setImageDrawable(drawable)
        initView(this)
    })

    constructor(bitmap: Bitmap, initView: SuperImageView.() -> Unit = {}) : this({
        this.setImageBitmap(bitmap)
        initView(this)
    })

    override fun getMinListItemHeight():Int
    {
        return SIZE_48
    }

    override fun getTopPadding(minHeight: Int): Int {
        if (minHeight >= SIZE_56)
            return SIZE_16
        else
            return SIZE_12
    }

    override fun getEndMargin(): Int {
        return SIZE_32
    }


    override fun getConstraintWidth(): Int
    {
        return SIZE_24
    }

    override fun getConstraintHeight(): Int
    {
        return SIZE_24
    }
}