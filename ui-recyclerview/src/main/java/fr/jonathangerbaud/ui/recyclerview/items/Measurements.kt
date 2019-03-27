package fr.jonathangerbaud.ui.recyclerview.items

/**
 * All size measurements are in pixels
 */
interface Measurements
{
    fun getMinListItemHeight():Int
    fun getTopPadding(minHeight:Int):Int
    fun getStartMarginIfStartComponent():Int
    fun getEndMarginIfEndComponent():Int
    fun getEndMargin():Int
    fun getVerticalGravity():Int

    /**
     * @return ConstraintSet size constant
     */
    fun getWidth(): Int
    /**
     * @return ConstraintSet size constant
     */
    fun getHeight(): Int
}