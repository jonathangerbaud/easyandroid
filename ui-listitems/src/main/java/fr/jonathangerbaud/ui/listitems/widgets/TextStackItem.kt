package fr.jonathangerbaud.ui.listitems.widgets

import androidx.appcompat.widget.LinearLayoutCompat
import fr.jonathangerbaud.ui.listitems.DefaultListItem


open class TextStackItem(initView: LinearLayoutCompat.() -> Unit = {}) : DefaultListItem<LinearLayoutCompat>(::LinearLayoutCompat, initView)
{
    val stack: ArrayList<TextItem> = ArrayList()

    override fun beforeApplyingInit(view: LinearLayoutCompat)
    {
        view.orientation = LinearLayoutCompat.VERTICAL

        for (textItem in stack)
            view.addView(textItem.build(view.context))
    }

    fun addText(item: TextItem):TextStackItem {
        stack.add(item)
        return this
    }

    override fun getMinListItemHeight():Int
    {
        return when (stack.size)
        {
            1 -> SIZE_48
            2 -> SIZE_64
            3 -> SIZE_88
            else -> SIZE_88
        }
    }

    override fun getTopPadding(minHeight:Int):Int
    {
        return SIZE_16//if (minHeight < SIZE_72) SIZE_16 else SIZE_24
    }

    override fun getConstraintWidth(): Int
    {
        return SIZE_MATCH_PARENT
    }
}