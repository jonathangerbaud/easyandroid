package fr.jonathangerbaud.ui.listitems.widgets

import android.content.Context
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import fr.jonathangerbaud.ui.listitems.DefaultRowItemSpec
import fr.jonathangerbaud.ui.listitems.RowItem
import fr.jonathangerbaud.ui.listitems.RowItemSpec
import fr.jonathangerbaud.ui.widgets.WidgetBuilder


class TextStackItem : WidgetBuilder<TextStackItem>(), RowItem
{
    val stack: ArrayList<TextItem> = ArrayList()

    override fun buildView(context: Context): View
    {
        val view = LinearLayoutCompat(context)
        view.orientation = LinearLayoutCompat.VERTICAL

        for (textItem in stack)
            view.addView(textItem.buildView(context))

        view.setPadding(0, 0, 0, DefaultRowItemSpec.SIZE_16)

        return view
    }

    fun addText(item: TextItem) = applySelf { stack.add(item) }

    override fun getRowItemSpecs(): RowItemSpec
    {
        return CustomRowItemSpec(stack.size)
    }

    private class CustomRowItemSpec(val stackSize:Int) : DefaultRowItemSpec()
    {
        override fun getMinListItemHeight():Int
        {
            return when (stackSize)
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

        override fun getWidth(): Int
        {
            return SIZE_MATCH_PARENT
        }

        override fun getHeight(): Int
        {
            return SIZE_WRAP_CONTENT
        }
    }
}