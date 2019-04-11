package fr.jonathangerbaud.ui.recyclerview

import androidx.recyclerview.widget.GridLayoutManager

open class GridSpanSizeLookup(private val defaultSpanSize:Int = 1) : GridLayoutManager.SpanSizeLookup()
{
    override fun getSpanSize(position: Int): Int
    {
        return defaultSpanSize
    }

    /*override fun getSpanIndex(position: Int, spanCount: Int): Int
    {
        var column = 0
        var itemSpan = 0
        var lineSpan = 0

        for (i in 0 until position)
        {
            itemSpan = getSpanSize(position)

            // Item can't fit because it span value is greater than the layout manager's one
            if (itemSpan > spanCount)
                throw IndexOutOfBoundsException("GridSpanSizeLookup : Can't have item at position $i defined with a span value of $itemSpan when total recycler view's layout manager span is only $spanCount")

            // item goes into next line
            if (lineSpan + itemSpan > spanCount)
            {
                column = 0
                lineSpan = column + itemSpan
            }
            else
            {
                column = lineSpan
                lineSpan += itemSpan
            }
        }

        return column
    }*/
}