package fr.jonathangerbaud.ui.recyclerview.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/***
 * Made by Lokesh Desai (Android4Dev)
 */
class StaggeredGridDivider(val spacingPx: Int, private val gridSize: Int) : RecyclerView.ItemDecoration()
{
    private var mNeedLeftSpacing = false

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        val frameWidth = ((parent.width - spacingPx.toFloat() * (gridSize - 1)) / gridSize).toInt()
        val padding = parent.width / gridSize - frameWidth
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition

        outRect.left = 0
        outRect.right = 0

        if (itemPosition < gridSize)
            outRect.top = 0
        else
            outRect.top = spacingPx

        if (itemPosition % gridSize == 0)
        {
            outRect.left = 0
            outRect.right = padding
            mNeedLeftSpacing = true
        }
        else if ((itemPosition + 1) % gridSize == 0)
        {
            mNeedLeftSpacing = false
            outRect.right = 0
            outRect.left = padding
        }
        else if (mNeedLeftSpacing)
        {
            mNeedLeftSpacing = false
            outRect.left = spacingPx - padding
            if ((itemPosition + 2) % gridSize == 0)
            {
                outRect.right = spacingPx - padding
            }
            else
            {
                outRect.right = spacingPx / 2
            }
        }
        else if ((itemPosition + 2) % gridSize == 0)
        {
            mNeedLeftSpacing = false
            outRect.left = spacingPx / 2
            outRect.right = spacingPx - padding
        }
        else
        {
            mNeedLeftSpacing = false
            outRect.left = spacingPx / 2
            outRect.right = spacingPx / 2
        }
        outRect.bottom = 0
    }
}