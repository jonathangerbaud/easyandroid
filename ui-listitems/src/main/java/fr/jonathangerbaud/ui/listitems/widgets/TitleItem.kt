package fr.jonathangerbaud.ui.listitems.widgets

import android.widget.TextView
import androidx.annotation.StringRes
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.listitems.style.MaterialListTitleStyle


open class TitleItem(initView: TextView.() -> Unit = {}) : TextItem(initView)
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
    }

    override fun getTextBaseline(minHeight: Int, position: Int, count: Int): Int
    {
        // up to 64 should have only one text
        if (minHeight < SIZE_56)
        {
            return SIZE_28
        }
        else if (minHeight < SIZE_64)
            return SIZE_32
        else if (minHeight < SIZE_72)
            return if (position == 0) SIZE_28 else SIZE_20
        else
            return if (position == 0) SIZE_32 else SIZE_20
    }
}