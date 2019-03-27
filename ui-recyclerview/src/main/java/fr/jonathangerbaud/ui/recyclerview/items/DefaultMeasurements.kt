package fr.jonathangerbaud.ui.recyclerview.items

import android.view.Gravity
import androidx.constraintlayout.widget.ConstraintSet
import fr.jonathangerbaud.core.ext.d
import fr.jonathangerbaud.core.util.ResUtils


open class DefaultMeasurements: Measurements
{
    val SIZE_8:Int = ResUtils.getDpInPx(8)
    val SIZE_12:Int = ResUtils.getDpInPx(12)
    val SIZE_16:Int = ResUtils.getDpInPx(16)
    val SIZE_24:Int = ResUtils.getDpInPx(24)
    val SIZE_32:Int = ResUtils.getDpInPx(32)
    val SIZE_40:Int = ResUtils.getDpInPx(40)
    val SIZE_48:Int = ResUtils.getDpInPx(48)
    val SIZE_56:Int = ResUtils.getDpInPx(56)
    val SIZE_72:Int = ResUtils.getDpInPx(72)
    val SIZE_88:Int = ResUtils.getDpInPx(88)

    val SIZE_MATCH_PARENT = ConstraintSet.MATCH_CONSTRAINT
    val SIZE_WRAP_CONTENT = ConstraintSet.WRAP_CONTENT

    protected fun getSize(dp: Int):Int
    {
        return ResUtils.getDpInPx(dp)
    }

    override fun getMinListItemHeight():Int
    {
        return SIZE_48
    }

    override fun getTopPadding(minHeight:Int):Int
    {
        d("minHeight $minHeight size56 $SIZE_56")
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