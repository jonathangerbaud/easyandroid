package fr.jonathangerbaud.ui.core.ext

import android.content.res.ColorStateList
import android.widget.ImageView
import fr.jonathangerbaud.core.util.ResUtils

fun ImageView.setImageResourceOrHide(res: Int)
{
    if (res <= 0)
    {
        hide()
    }
    else
    {
        setImageResource(res)
        show()
    }
}

fun ImageView.setTint(color:Int) { imageTintList = ColorStateList.valueOf(color) }
fun ImageView.setTintRes(colorRes:Int) { imageTintList = ColorStateList.valueOf(ResUtils.getColor(colorRes, context)) }