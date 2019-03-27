package fr.jonathangerbaud.ui.recyclerview.items

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import fr.jonathangerbaud.ui.recyclerview.R


class SingleLineItem(context: Context) : ListItem(context)
{
    var title: TextView
        private set

    var icon: ImageView
        private set

    init {
        icon = startContent as ImageView
        title = mainContent as TextView
    }

    companion object
    {
        fun build(context:Context):SingleLineItem
        {
            return ListItem.Builder()
                .startContent(IconBuilder().drawable(R.drawable.jg_ic_arrow_left))
                .mainContent(SingleLineBuilder().text("hello"))
                .build(context) as SingleLineItem
        }
    }
}