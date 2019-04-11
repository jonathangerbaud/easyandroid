package fr.jonathangerbaud.ui.listitems

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.TextViewCompat
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.constraintlayout.ext.*
import fr.jonathangerbaud.ui.listitems.widgets.TextItem
import fr.jonathangerbaud.ui.listitems.widgets.TextStackItem
import fr.jonathangerbaud.ui.widgets.WidgetBuilder

open class Row : ConstraintLayout
{
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var startContent: View? = null
    var mainContent: View? = null
    var endContent: View? = null

    class Builder : WidgetBuilder<Row.Builder>()
    {
        private var startItem: RowItem? = null
        private var mainItem: RowItem? = null
        private var endItem: RowItem? = null

        fun startItem(item: RowItem): Builder
        {
            startItem = item
            return this
        }

        fun mainItem(item: RowItem): Builder
        {
            this.mainItem = item
            return this
        }

        fun endItem(item: RowItem): Builder
        {
            endItem = item
            return this
        }

        override fun createView(context: Context): Row
        {
            return Row(context)
        }

        fun build(parent:ViewGroup) : Row
        {
            return build(parent.context, Row(parent.context))
        }

        fun build(context: Context, view: Row): Row
        {
            applyViewAttributes(view)

            val startView: View? = startItem?.build(context)
            val mainView: View? = mainItem?.build(context)
            val endView: View? = endItem?.build(context)

            val startSpecs = startItem?.getRowItemSpecs()
            val mainSpecs = mainItem?.getRowItemSpecs()
            val endSpecs = endItem?.getRowItemSpecs()

            // Define min height
            var height = 0

            startSpecs?.run { height = Math.max(height, getMinListItemHeight()) }
            mainSpecs?.run { height = Math.max(height, getMinListItemHeight()) }
            endSpecs?.run { height = Math.max(height, getMinListItemHeight()) }

            startView?.apply { id = R.id.startContent }
            mainView?.apply { id = R.id.mainContent }
            endView?.apply { id = R.id.endContent }

            // Define constraints
            val cs = ConstraintSet()

            startView?.let {
                cs.alignStartParentStart(it, startSpecs!!.getStartMarginIfStartComponent())

                val gravity = startSpecs.getVerticalGravity()
                if (gravity == Gravity.CENTER_VERTICAL || gravity == Gravity.CENTER)
                    cs.centerInParentVertically(it)
                else if (gravity == Gravity.BOTTOM)
                    cs.alignParentBottom(it, startSpecs.getTopPadding(height))
                else
                    alignTop(cs, height, it, startItem!!, startSpecs)

                cs.constrainWidth(it.id, startSpecs.getWidth())
                cs.constrainHeight(it.id, startSpecs.getHeight())
            }

            mainView?.let {
                alignTop(cs, height, it, mainItem!!, mainSpecs!!)

                if (startView != null)
                    cs.alignStartToEnd(startView, it, startSpecs!!.getEndMargin())
                else
                    cs.alignStartParentStart(it, mainSpecs.getStartMarginIfStartComponent())

                if (endView != null)
                    cs.alignEndToStart(it, endView, mainSpecs.getEndMargin())
                else
                    cs.alignEndParentEnd(it, mainSpecs.getEndMarginIfEndComponent())

                cs.constrainWidth(it.id, mainSpecs.getWidth())
                cs.constrainHeight(it.id, mainSpecs.getHeight())
            }

            endView?.let {
                cs.alignEndParentEnd(it, endSpecs!!.getEndMarginIfEndComponent())

                val gravity = endSpecs.getVerticalGravity()
                if (gravity == Gravity.CENTER_VERTICAL || gravity == Gravity.CENTER)
                    cs.centerInParentVertically(it)
                else if (gravity == Gravity.BOTTOM)
                    cs.alignParentBottom(it, endSpecs.getTopPadding(height))
                else
                    alignTop(cs, height, it, endItem!!, endSpecs)

                cs.constrainWidth(it.id, endSpecs.getWidth())
                cs.constrainHeight(it.id, endSpecs.getHeight())
            }

            view.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            view.minHeight = height

            // Add children
            startView?.let { view.addView(startView) }
            mainView?.let { view.addView(mainView) }
            endView?.let { view.addView(endView) }

            cs.applyTo(view)

            view.startContent = startView
            view.mainContent = mainView
            view.endContent = endView

            backgroundColor?.let { view.setBackgroundColor(it) }

            return view
        }

        private fun alignTop(cs: ConstraintSet, height: Int, view: View, item: RowItem, specs:RowItemSpec)
        {
            if (item is TextItem)
            {
                cs.alignParentTop(view)
                TextViewCompat.setFirstBaselineToTopHeight(
                    view as TextView,
                    specs.getTextBaseline(height, 1, 1)
                )
            }
            else if (item is TextStackItem)
            {
                cs.alignParentTop(view)
                item.stack.forEachIndexed { index, textItem ->
                    TextViewCompat.setFirstBaselineToTopHeight(
                        (view as ViewGroup).getChildAt(index) as TextView,
                        textItem.getRowItemSpecs().getTextBaseline(height, index, item.stack.size)
                    )
                }
            }
            else
            {
                cs.alignParentTop(view, specs.getTopPadding(height))
            }
        }
    }
}