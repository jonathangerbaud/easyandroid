package fr.jonathangerbaud.ui.recyclerview.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridDivider2(private val spacing:Int) : RecyclerView.ItemDecoration()
{
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        val params = view.layoutParams as GridLayoutManager.LayoutParams
        val manager = parent.layoutManager as GridLayoutManager

        if (params.viewAdapterPosition + params.spanSize <= manager.spanCount)
            outRect.top = 0
        else
            outRect.top = spacing

        if (params.spanIndex == 0)
            outRect.left = spacing
        else
            outRect.left = spacing / 2

        if (params.spanIndex + params.spanSize == manager.spanCount)
            outRect.right = spacing
        else
            outRect.right = spacing / 2
    }
}