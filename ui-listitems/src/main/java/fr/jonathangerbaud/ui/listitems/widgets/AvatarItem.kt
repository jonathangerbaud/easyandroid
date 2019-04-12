package fr.jonathangerbaud.ui.listitems.widgets

import android.view.View
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP
import fr.jonathangerbaud.core.util.Dimens
import fr.jonathangerbaud.ui.image.MaskOptions
import fr.jonathangerbaud.ui.image.PathHelper
import fr.jonathangerbaud.ui.image.SuperImageView
import fr.jonathangerbaud.ui.listitems.DefaultRowItemSpec
import fr.jonathangerbaud.ui.listitems.RowItem
import fr.jonathangerbaud.ui.listitems.RowItemSpec
import fr.jonathangerbaud.ui.widgets.SuperImageBuilder

class AvatarItem : SuperImageBuilder<AvatarItem>(), RowItem
{
    private var maskOptions:MaskOptions? = null

    override fun applyViewAttributes(view: View)
    {
        super.applyViewAttributes(view)
        val view = view as SuperImageView

        maskOptions?.let { view.setMaskOptions(it) }
    }

    fun circle() = applySelf { maskOptions = MaskOptions.Builder(PathHelper.circle(Dimens.dpF(56))).build()}

    fun roundSquare(@Dimension(unit = DP) angleInDp:Int) = applySelf {
        val size = Dimens.dpF(56)
        maskOptions = MaskOptions.Builder(PathHelper.roundedRect(size, size, Dimens.dpF(angleInDp))).build()
    }

    override fun getRowItemSpecs(): RowItemSpec
    {
        return CustomRowItemSpec()
    }

    private class CustomRowItemSpec : DefaultRowItemSpec()
    {
        override fun getMinListItemHeight():Int
        {
            return SIZE_56
        }

        override fun getTopPadding(minHeight:Int):Int
        {
            return if (minHeight < SIZE_72) SIZE_8 else SIZE_16
        }

        override fun getWidth(): Int
        {
            return SIZE_40
        }

        override fun getHeight(): Int
        {
            return SIZE_40
        }
    }
}