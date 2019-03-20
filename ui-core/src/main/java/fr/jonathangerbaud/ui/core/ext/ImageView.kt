package fr.jonathangerbaud.ui.core.ext

import android.widget.ImageView

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