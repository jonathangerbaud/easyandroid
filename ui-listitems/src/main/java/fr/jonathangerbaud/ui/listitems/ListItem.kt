package fr.jonathangerbaud.ui.listitems

import android.content.Context
import android.view.View


interface ListItem<T:View>
{
    fun build(context: Context):T

    fun getMinListItemHeight():Int
    fun getTopPadding(minHeight:Int):Int
    fun getStartMarginIfStartComponent():Int
    fun getEndMarginIfEndComponent():Int
    fun getEndMargin():Int
    fun getVerticalGravity():Int

    fun getTextBaseline(minHeight: Int, position: Int = 1, count: Int = 1): Int = 0

    /**
     * @return ConstraintSet size constant
     */
    fun getConstraintWidth(): Int
    /**
     * @return ConstraintSet size constant
     */
    fun getConstraintHeight(): Int
}