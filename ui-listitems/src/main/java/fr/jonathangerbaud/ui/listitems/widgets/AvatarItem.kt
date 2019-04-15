package fr.jonathangerbaud.ui.listitems.widgets

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import fr.jonathangerbaud.core.util.Dimens
import fr.jonathangerbaud.ui.image.MaskOptions
import fr.jonathangerbaud.ui.image.PathHelper
import fr.jonathangerbaud.ui.image.SuperImageView


open class AvatarItem(initView: SuperImageView.() -> Unit = {}) : IconItem(initView)
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

    private var maskOptions: MaskOptions? = null

    override fun beforeApplyingInit(view: SuperImageView)
    {
        maskOptions?.let { view.setMaskOptions(it) }
    }

    fun circle(): AvatarItem
    {
        maskOptions = MaskOptions.Builder(PathHelper.circle(Dimens.dpF(56))).build()
        return this
    }

    fun roundSquare(@Dimension(unit = Dimension.DP) angleInDp: Int): AvatarItem
    {
        val size = Dimens.dpF(56)
        maskOptions = MaskOptions.Builder(PathHelper.roundedRect(size, size, Dimens.dpF(angleInDp))).build()
        return this
    }

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