package fr.jonathangerbaud.ui.listitems.widgets

import android.widget.TextView
import androidx.annotation.StringRes
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.listitems.style.MaterialListOverlineStyle


open class OverlineItem(initView: TextView.() -> Unit = {}) : TextItem(initView)
{
    constructor(text: String, initView: TextView.() -> Unit = {}) : this({
        this.text = text
        initView(this)
    })

    constructor(@StringRes stringRes: Int, initView: TextView.() -> Unit = {}) : this({
        this.text = ResUtils.getString(stringRes)
        initView(this)
    })

    override fun beforeApplyingInit(view: TextView)
    {
        MaterialListOverlineStyle().applyDefaultStyle(view)
    }

    override fun getTextBaseline(minHeight: Int, position: Int, count: Int): Int
    {
        if (minHeight < SIZE_88)
            return SIZE_24
        else
            return SIZE_28
    }
}