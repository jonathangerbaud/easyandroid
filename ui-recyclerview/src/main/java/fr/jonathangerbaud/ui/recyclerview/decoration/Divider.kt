package fr.jonathangerbaud.ui.recyclerview.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.jonathangerbaud.core.util.Dimens
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.recyclerview.DataAdapter
import kotlin.reflect.KClass

/**
 * Creates a divider [RecyclerView.ItemDecoration] that can be used with a
 * [LinearLayoutManager].
 *
 * @param context Current context, it will be used to access resources.
 * @param orientation Divider orientation. Should be [.HORIZONTAL] or [.VERTICAL].
 */
class Divider(orientation: Int = VERTICAL, private var drawable: Drawable? = null) : RecyclerView.ItemDecoration()
{
    constructor(orientation: Int, @DrawableRes drawableRes: Int) : this(orientation, ResUtils.getDrawable(drawableRes))

    companion object
    {
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL
    }

    /**
     * Current orientation. Either [.HORIZONTAL] or [.VERTICAL].
     */
    private var orientation: Int = orientation
        /**
         * Sets the orientation for this divider. This should be called if
         * [RecyclerView.LayoutManager] changes orientation.
         *
         * @param orientation [.HORIZONTAL] or [.VERTICAL]
         */
        set(value)
        {
            if (value != HORIZONTAL && value != VERTICAL)
            {
                throw IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL"
                )
            }
            field = value
        }

    private val mBounds = Rect()

    var showLast:Boolean = false
    var range:IntRange? = null

    var startMargin:Int = 0
    var endMargin:Int = 0

    private var viewType:KClass<*>? = null
    private var dataType:KClass<*>? = null

    fun setDividerSize(sizeInPx: Int)
    {
        drawable = ShapeDrawable().apply {
            intrinsicWidth = sizeInPx
            intrinsicHeight = sizeInPx
            paint.color = 0x00000000.toInt()
        }
    }

    fun setDividerSizeDp(sizeInDp: Int)
    {
        setDividerSize(Dimens.dp(sizeInDp))
    }

    fun setDividerSizeRes(@DimenRes dimenRes: Int)
    {
        setDividerSize(ResUtils.getDimensionPxSize(dimenRes))
    }

    fun setColor(@ColorInt color: Int)
    {
        drawable?.setTint(color)

        drawable?.let {
            if (it is ShapeDrawable)
                it.paint.color = 0xFF000000.toInt()
        }
    }

    fun setColorRes(@ColorRes colorRes:Int, context:Context? = null)
    {
        drawable?.setTint(ResUtils.getColor(colorRes, context))
    }

    fun setMargins(marginInPx: Int)
    {
        startMargin = marginInPx
        endMargin = marginInPx
    }

    fun setMarginsRes(@DimenRes dimenRes:Int)
    {
        startMargin = ResUtils.getDimensionPxSize(dimenRes)
        endMargin = ResUtils.getDimensionPxSize(dimenRes)
    }

    fun showOnlyBetweenViewsOfType(type:KClass<*>)
    {
        viewType = type
    }

    fun showOnlyBetweenDataOfType(type:KClass<*>)
    {
        dataType = type
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State)
    {
        if (parent.layoutManager == null || drawable == null)
            return

        if (orientation == VERTICAL)
            drawVertical(c, parent)
        else
            drawHorizontal(c, parent)
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView)
    {
        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding)
        {
            left = parent.paddingLeft + startMargin
            right = parent.width - parent.paddingRight - endMargin
            canvas.clipRect(
                left, parent.paddingTop, right,
                parent.height - parent.paddingBottom
            )
        }
        else
        {
            left = startMargin
            right = parent.width - endMargin
        }

        val childCount = parent.childCount
        for (i in 0 until childCount)
        {
            val child = parent.getChildAt(i)

            if (!shouldDrawDividerForView(child, parent))
                continue

            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom = mBounds.bottom + Math.round(child.translationY)
            val top = bottom - drawable!!.intrinsicHeight
            drawable!!.setBounds(left, top, right, bottom)
            drawable!!.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView)
    {
        canvas.save()
        val top: Int
        val bottom: Int

        if (parent.clipToPadding)
        {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(
                parent.paddingLeft, top,
                parent.width - parent.paddingRight, bottom
            )
        }
        else
        {
            top = 0
            bottom = parent.height
        }

        val childCount = parent.childCount
        for (i in 0 until childCount)
        {
            val child = parent.getChildAt(i)

            if (!shouldDrawDividerForView(child, parent))
                continue

            parent.layoutManager!!.getDecoratedBoundsWithMargins(child, mBounds)
            val right = mBounds.right + Math.round(child.translationX)
            val left = right - drawable!!.intrinsicWidth
            drawable!!.setBounds(left, top, right, bottom)
            drawable!!.draw(canvas)
        }
        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    )
    {
        if (drawable == null || !shouldDrawDividerForView(view, parent))
            outRect.set(0, 0, 0, 0)
        else if (orientation == VERTICAL)
            outRect.set(0, 0, 0, drawable!!.intrinsicHeight)
        else
            outRect.set(0, 0, drawable!!.intrinsicWidth, 0)
    }

    private fun shouldDrawDividerForView(view:View, recyclerView:RecyclerView):Boolean
    {
        var shouldDraw = true

        val position = recyclerView.getChildAdapterPosition(view)

        recyclerView.adapter?.let {
            shouldDraw = position < it.itemCount - 1

            val adapter = it
            viewType?.let { type ->

                shouldDraw = shouldDraw && type.java.isInstance(view)

                if (shouldDraw && position < adapter.itemCount - 1 )
                {
                    val nextView:View? = recyclerView.layoutManager?.findViewByPosition(position + 1)

                    if (nextView != null)
                        shouldDraw = shouldDraw && type.java.isInstance(nextView)
                }
            }

            if (it is DataAdapter)
            {
                dataType?.let { type ->
                    val data = it.getDataAtPosition(position)

                    shouldDraw = shouldDraw && type.java.isInstance(data)

                    if (shouldDraw && position < adapter.itemCount - 1)
                    {
                        val nextData = it.getDataAtPosition(position + 1)
                        shouldDraw = shouldDraw && type.java.isInstance(nextData)

                    }
                }
            }
        }

        range?.let {
            shouldDraw = shouldDraw && it.contains(position)
        }

        return shouldDraw
    }
}