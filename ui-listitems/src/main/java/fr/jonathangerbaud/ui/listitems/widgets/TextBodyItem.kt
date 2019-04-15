package fr.jonathangerbaud.ui.listitems.widgets

import android.widget.TextView
import androidx.annotation.StringRes
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.listitems.style.MaterialListBodyStyle


open class TextBodyItem(initView: TextView.() -> Unit = {}) : TextItem(initView)
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
        MaterialListBodyStyle().applyDefaultStyle(view)
    }
}