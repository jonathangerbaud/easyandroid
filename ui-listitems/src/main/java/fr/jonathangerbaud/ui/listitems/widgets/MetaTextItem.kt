package fr.jonathangerbaud.ui.listitems.widgets

import android.widget.TextView
import androidx.annotation.StringRes
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.core.text.MaterialTypography
import fr.jonathangerbaud.ui.listitems.style.MaterialListTitleStyle


open class MetaTextItem(initView: TextView.() -> Unit = {}) : TextItem(initView)
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
        MaterialListTitleStyle().applyDefaultStyle(view)
        view.textSize = MaterialTypography.TypeScale.SUBTITLE2.size
    }

    override fun getTextBaseline(minHeight: Int, position: Int, count: Int): Int
    {
        return SIZE_28
    }
}