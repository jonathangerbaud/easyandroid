package fr.jonathangerbaud.ui.listitems

import android.view.Gravity
import androidx.constraintlayout.widget.ConstraintSet
import fr.jonathangerbaud.core.util.Dimens
import fr.jonathangerbaud.core.util.ResUtils


open class DefaultRowItemSpec: RowItemSpec
{
    companion object
    {
        val SIZE_8:Int = Dimens.dp(8)
        val SIZE_12:Int = Dimens.dp(12)
        val SIZE_16:Int = Dimens.dp(16)
        val SIZE_20:Int = Dimens.dp(20)
        val SIZE_24:Int = Dimens.dp(24)
        val SIZE_28:Int = Dimens.dp(28)
        val SIZE_32:Int = Dimens.dp(32)
        val SIZE_40:Int = Dimens.dp(40)
        val SIZE_48:Int = Dimens.dp(48)
        val SIZE_56:Int = Dimens.dp(56)
        val SIZE_64:Int = Dimens.dp(64)
        val SIZE_72:Int = Dimens.dp(72)
        val SIZE_88:Int = Dimens.dp(88)

        val SIZE_MATCH_PARENT = ConstraintSet.MATCH_CONSTRAINT
        val SIZE_WRAP_CONTENT = ConstraintSet.WRAP_CONTENT
    }

    protected fun getSize(dp: Int):Int
    {
        return Dimens.dp(dp)
    }

    override fun getMinListItemHeight():Int
    {
        return SIZE_48
    }

    override fun getTopPadding(minHeight:Int):Int
    {
        return if (minHeight < SIZE_56) SIZE_8 else SIZE_16
    }

    override fun getStartMarginIfStartComponent():Int
    {
        return SIZE_16
    }

    override fun getEndMarginIfEndComponent():Int
    {
        return SIZE_16
    }

    override fun getEndMargin():Int
    {
        return SIZE_16
    }

    override fun getVerticalGravity(): Int
    {
        return Gravity.TOP
    }

    override fun getWidth(): Int
    {
        return ConstraintSet.WRAP_CONTENT
    }

    override fun getHeight(): Int
    {
        return ConstraintSet.WRAP_CONTENT
    }
}