package fr.jonathangerbaud.sample

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import fr.jonathangerbaud.ui.listitems.Row
import fr.jonathangerbaud.ui.listitems.widgets.SmallIconItem
import fr.jonathangerbaud.ui.listitems.widgets.SwitchItem
import fr.jonathangerbaud.ui.listitems.widgets.TitleItem
import fr.jonathangerbaud.ui.recyclerview.R


class SingleLineItem(context: Context) : Row(context)
{
    var title: TextView
        private set

    var icon: ImageView
        private set

    init {
        Row.Composer()
            .startItem(SmallIconItem(R.drawable.notification_template_icon_bg))
            .endItem(SwitchItem())
            .mainItem(TitleItem("hello"))
            .build(this)

        icon = startContent as ImageView
        title = mainContent as TextView
    }
}