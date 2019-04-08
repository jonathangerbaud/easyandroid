package fr.jonathangerbaud.core.util

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import fr.jonathangerbaud.core.BaseApp


object Dimens
{
    private val context: Context
        get() = BaseApp.instance

    private val res: Resources
        get() = context.resources

    private fun convert(unit:Int, value:Float):Float = TypedValue.applyDimension(unit, value, res.displayMetrics)

    /**
     * Return the value in pixels corresponding to the passed parameter in dp
     * @param value the value, in dp, to convert in pixels
     * @Return the converted value in pixels, casted to Int
     */
    fun dp(value:Int):Int = convert(TypedValue.COMPLEX_UNIT_DIP, value.toFloat()).toInt()
    /**
     * Return the value in pixels corresponding to the passed parameter in dp
     * @param value the value, in dp, to convert in pixels
     * @Return the converted value in pixels, casted to Int
     */
    fun dp(value:Float):Int = convert(TypedValue.COMPLEX_UNIT_DIP, value).toInt()
    /**
     * Return the value in pixels corresponding to the passed parameter in dp. Same as the <code>dp</code>, except that
     * it returns a Float instead of an Int
     * @param value the value, in dp, to convert in pixels
     * @Return the converted value in pixels, casted to Float
     */
    fun dpF(value:Int):Float = convert(TypedValue.COMPLEX_UNIT_DIP, value.toFloat())
    /**
     * Return the value in pixels corresponding to the passed parameter in dp. Same as the <code>dp</code>, except that
     * it returns a Float instead of an Int
     * @param value the value, in dp, to convert in pixels
     * @Return the converted value in pixels, casted to Float
     */
    fun dpF(value:Float):Float = convert(TypedValue.COMPLEX_UNIT_DIP, value)

    /**
     * Return the value in pixels corresponding to the passed parameter in sp
     * @param value the value, in sp, to convert in pixels
     * @Return the converted value in pixels, casted to Int
     */
    fun sp(value: Int): Int = convert(TypedValue.COMPLEX_UNIT_SP, value.toFloat()).toInt()
    /**
     * Return the value in pixels corresponding to the passed parameter in sp. Same as the <code>sp</code>, except that
     * it returns a Float instead of an Int
     * @param value the value, in sp, to convert in pixels
     * @Return the converted value in pixels, casted to Float
     */
    fun spF(value: Int): Float = convert(TypedValue.COMPLEX_UNIT_SP, value.toFloat())
}