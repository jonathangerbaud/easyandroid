package fr.jonathangerbaud.ui.listitems.widgets

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import fr.jonathangerbaud.ui.image.SuperImageView
import fr.jonathangerbaud.ui.listitems.DefaultListItem


open class IconItem(initView: SuperImageView.() -> Unit = {}) :
    DefaultListItem<SuperImageView>(::SuperImageView, initView)
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

    override fun getMinListItemHeight(): Int
    {
        return SIZE_56
    }

    override fun getTopPadding(minHeight: Int): Int
    {
        return if (minHeight < SIZE_72) SIZE_8 else SIZE_16
    }

    override fun getConstraintWidth(): Int
    {
        return SIZE_40
    }

    override fun getConstraintHeight(): Int
    {
        return SIZE_40
    }
}