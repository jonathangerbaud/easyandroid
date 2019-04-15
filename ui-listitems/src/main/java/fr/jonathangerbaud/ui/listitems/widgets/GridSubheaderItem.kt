package fr.jonathangerbaud.ui.listitems.widgets

import android.widget.TextView
import androidx.annotation.StringRes
import fr.jonathangerbaud.core.util.ResUtils


open class GridSubheaderItem(initView: TextView.() -> Unit = {}) : SubheaderItem(initView)
{
    constructor(text: String, initView: TextView.() -> Unit = {}) : this({
        this.text = text
        initView(this)
    })

    constructor(@StringRes stringRes: Int, initView: TextView.() -> Unit = {}) : this({
        this.text = ResUtils.getString(stringRes)
        initView(this)
    })

    override fun getStartMarginIfStartComponent(): Int
    {
        return 0
    }

    override fun getEndMarginIfEndComponent(): Int
    {
        return 0
    }
}