package fr.jonathangerbaud.ui.listitems.widgets

import android.widget.TextView
import androidx.annotation.StringRes
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.listitems.DefaultListItem


open class TextItem(initView: TextView.() -> Unit = {}) : DefaultListItem<TextView>(::TextView, initView)
{
    constructor(text: String, initView: TextView.() -> Unit = {}) : this({
        this.text = text
        initView(this)
    })

    constructor(@StringRes stringRes: Int, initView: TextView.() -> Unit = {}) : this({
        this.text = ResUtils.getString(stringRes)
        initView(this)
    })

    override fun getConstraintWidth(): Int
    {
        return SIZE_MATCH_PARENT
    }

    override fun getConstraintHeight(): Int
    {
        return SIZE_WRAP_CONTENT
    }
}