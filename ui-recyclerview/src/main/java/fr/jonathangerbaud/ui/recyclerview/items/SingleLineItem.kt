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
        ListItem.Builder()
            .startContent(SmallIconBuilder().drawable(R.drawable.abc_ic_go_search_api_material))
            .endContent(SwitchBuilder())
            .mainContent(SingleLineBuilder().text("hello"))
            .build(context, this)

        icon = startContent as ImageView
        title = mainContent as TextView
    }
}