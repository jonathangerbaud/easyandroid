package fr.jonathangerbaud.ui.recyclerview.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Apply a spacing around items of a grid. It works with GridLayoutManager and StaggeredGridLayoutManager
 */
class GridDivider3
/**
 * @param spacing the spacing between each cell. In px.
 */
    (private val spacing: Int) : RecyclerView.ItemDecoration()
{

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        val gridItemData = extractGridData(parent, view)
        val spanCount = gridItemData.spanCount
        val spanIndex = gridItemData.spanIndex
        val spanSize = gridItemData.spanSize

        if (spanIndex == 0)
            outRect.left = 0
        else
            outRect.left = (spacing * ((spanCount - spanIndex) / spanCount.toFloat())).toInt()
//            outRect.left = (spacing * ((spanCount - spanIndex) / spanCount.toFloat())).toInt()

        if (spanIndex + spanSize == spanCount)
            outRect.right = 0
        else
            outRect.right = (spacing * ((spanIndex + spanSize) / spanCount.toFloat())).toInt()
//            outRect.right = (spacing * ((spanIndex + spanSize) / spanCount.toFloat())).toInt()

        outRect.bottom = spacing
    }

    private fun extractGridData(parent: RecyclerView, view: View): GridItemData
    {
        val layoutManager = parent.layoutManager
        return when (layoutManager)
        {
            is GridLayoutManager -> extractGridLayoutData((layoutManager as GridLayoutManager?)!!, view)
            is StaggeredGridLayoutManager -> extractStaggeredGridLayoutData((layoutManager as StaggeredGridLayoutManager?)!!, view)
            else -> throw UnsupportedOperationException("Bad layout params")
        }
    }

    private fun extractGridLayoutData(layoutManager: GridLayoutManager, view: View): GridItemData
    {
        val lp = view.layoutParams as GridLayoutManager.LayoutParams
        return GridItemData(
            layoutManager.spanCount,
            lp.spanIndex,
            lp.spanSize
        )
    }

    private fun extractStaggeredGridLayoutData(layoutManager: StaggeredGridLayoutManager, view: View): GridItemData
    {
        val lp = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        return GridItemData(
            layoutManager.spanCount,
            lp.spanIndex,
            if (lp.isFullSpan) layoutManager.spanCount else 1
        )
    }

    data class GridItemData constructor(val spanCount: Int, val spanIndex: Int, val spanSize: Int)
}