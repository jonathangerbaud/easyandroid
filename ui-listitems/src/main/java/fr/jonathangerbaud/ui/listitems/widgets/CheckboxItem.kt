package fr.jonathangerbaud.ui.listitems.widgets

import android.view.Gravity
import android.widget.CheckBox
import fr.jonathangerbaud.ui.listitems.DefaultListItem


open class CheckboxItem(initView: CheckBox.() -> Unit = {}) : DefaultListItem<CheckBox>(::CheckBox, initView)
{
    constructor(checked: Boolean, initView: CheckBox.() -> Unit = {}) : this({
        this.isChecked = checked
        initView(this)
    })

    override fun getMinListItemHeight(): Int
    {
        return SIZE_56
    }

    override fun getVerticalGravity(): Int
    {
        return Gravity.CENTER_VERTICAL
    }

    override fun getEndMargin(): Int
    {
        return SIZE_32
    }
}