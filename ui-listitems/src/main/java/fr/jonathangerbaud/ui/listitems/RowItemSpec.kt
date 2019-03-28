package fr.jonathangerbaud.ui.listitems

/**
 * All size measurements are in pixels
 */
interface RowItemSpec
{
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
    fun getWidth(): Int
    /**
     * @return ConstraintSet size constant
     */
    fun getHeight(): Int
}