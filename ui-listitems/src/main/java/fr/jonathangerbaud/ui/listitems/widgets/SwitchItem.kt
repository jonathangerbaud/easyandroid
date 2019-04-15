package fr.jonathangerbaud.ui.listitems.widgets

import android.view.Gravity
import androidx.appcompat.widget.SwitchCompat
import fr.jonathangerbaud.ui.listitems.DefaultListItem


open class SwitchItem(initView: SwitchCompat.() -> Unit = {}) : DefaultListItem<SwitchCompat>(::SwitchCompat, initView)
{
    constructor(checked: Boolean, initView: SwitchCompat.() -> Unit = {}) : this({
        this.isChecked = checked
        initView(this)
    })

    override fun getMinListItemHeight():Int
    {
        return SIZE_56
    }

    override fun getVerticalGravity(): Int {
        return Gravity.CENTER_VERTICAL
    }

    override fun getEndMargin(): Int {
        return SIZE_32
    }
}