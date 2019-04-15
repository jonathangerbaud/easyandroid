package fr.jonathangerbaud.ui.listitems

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import fr.jonathangerbaud.core.util.Dimens
import fr.jonathangerbaud.ui.core.view.V


abstract class DefaultListItem<T : View>(
    private val createView: (Context) -> T,
    private val initView: T.() -> Unit = {}
) : ListItem<T>
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

    override fun build(context: Context): T
    {
        return V.view(context, createView).apply(::beforeApplyingInit).apply(initView)
    }

    protected open fun beforeApplyingInit(view: T)
    {

    }

    override fun getMinListItemHeight(): Int
    {
        return SIZE_48
    }

    override fun getTopPadding(minHeight: Int): Int
    {
        return if (minHeight < SIZE_56) SIZE_8 else SIZE_16
    }

    override fun getStartMarginIfStartComponent(): Int
    {
        return SIZE_16
    }

    override fun getEndMarginIfEndComponent(): Int
    {
        return SIZE_16
    }

    override fun getEndMargin(): Int
    {
        return SIZE_16
    }

    override fun getVerticalGravity(): Int
    {
        return Gravity.TOP
    }

    override fun getConstraintWidth(): Int
    {
        return ConstraintSet.WRAP_CONTENT
    }

    override fun getConstraintHeight(): Int
    {
        return ConstraintSet.WRAP_CONTENT
    }
}