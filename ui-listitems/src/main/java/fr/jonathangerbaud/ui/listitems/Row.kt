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
import fr.jonathangerbaud.ui.constraintlayout.ext.*
import fr.jonathangerbaud.ui.listitems.widgets.TextItem
import fr.jonathangerbaud.ui.listitems.widgets.TextStackItem


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

    class Composer
    {
        private var startItem: ListItem<*>? = null
        private var mainItem: ListItem<*>? = null
        private var endItem: ListItem<*>? = null

        private var init: Row.() -> Unit = {}

        fun init(init: Row.() -> Unit): Composer
        {
            this.init = init
            return this
        }

        fun startItem(item: ListItem<*>): Composer
        {
            startItem = item
            return this
        }

        fun mainItem(item: ListItem<*>): Composer
        {
            this.mainItem = item
            return this
        }

        fun endItem(item: ListItem<*>): Composer
        {
            endItem = item
            return this
        }

        fun build(parent:ViewGroup) : Row
        {
            return build(parent.context)
        }

        fun build(context:Context) : Row
        {
            return build(Row(context))
        }

        fun build(view: Row): Row
        {
            view.apply(init)

            var startView: View? = null
            var mainView: View? = null
            var endView: View? = null

            // Define min height
            var height = 0

            startItem?.run { height = Math.max(height, getMinListItemHeight()) }
            mainItem?.run { height = Math.max(height, getMinListItemHeight()) }
            endItem?.run { height = Math.max(height, getMinListItemHeight()) }

            // Define constraints
            val cs = ConstraintSet()

            startItem?.let {
                startView = it.build(view.context).apply { id = R.id.startContent }
                view.addView(startView)
                @Suppress("NAME_SHADOWING") val startView = startView!!

                cs.alignStartParentStart(startView, it.getStartMarginIfStartComponent())

                val gravity = it.getVerticalGravity()
                if (gravity == Gravity.CENTER_VERTICAL || gravity == Gravity.CENTER)
                    cs.centerInParentVertically(startView)
                else if (gravity == Gravity.BOTTOM)
                    cs.alignParentBottom(startView, it.getTopPadding(height))
                else
                    alignTop(cs, height, startView, it)

                cs.constrainWidth(startView.id, it.getConstraintWidth())
                cs.constrainHeight(startView.id, it.getConstraintHeight())
            }

            endItem?.let {
                endView = it.build(view.context).apply { id = R.id.endContent }
                view.addView(endView)
                @Suppress("NAME_SHADOWING") val endView = endView!!

                cs.alignEndParentEnd(endView, it.getEndMarginIfEndComponent())

                val gravity = it.getVerticalGravity()
                if (gravity == Gravity.CENTER_VERTICAL || gravity == Gravity.CENTER)
                    cs.centerInParentVertically(endView)
                else if (gravity == Gravity.BOTTOM)
                    cs.alignParentBottom(endView, it.getTopPadding(height))
                else
                    alignTop(cs, height, endView, it)

                cs.constrainWidth(endView.id, it.getConstraintWidth())
                cs.constrainHeight(endView.id, it.getConstraintHeight())
            }

            mainItem?.let {
                mainView = it.build(view.context).apply { id = R.id.mainContent }
                view.addView(mainView)
                @Suppress("NAME_SHADOWING") val mainView = mainView!!

                alignTop(cs, height, mainView, it)

                if (startView != null)
                    cs.alignStartToEnd(startView!!, mainView, startItem!!.getEndMargin())
                else
                    cs.alignStartParentStart(mainView, it.getStartMarginIfStartComponent())

                if (endView != null)
                    cs.alignEndToStart(mainView, endView!!, it.getEndMargin())
                else
                    cs.alignEndParentEnd(mainView, it.getEndMarginIfEndComponent())

                cs.constrainWidth(mainView.id, it.getConstraintWidth())
                cs.constrainHeight(mainView.id, it.getConstraintHeight())
            }

            view.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            view.minHeight = height

            cs.applyTo(view)

            view.startContent = startView
            view.mainContent = mainView
            view.endContent = endView

            return view
        }

        private fun alignTop(cs: ConstraintSet, height: Int, view: View, item: ListItem<*>)
        {
            when (item)
            {
                is TextItem ->
                {
                    cs.alignParentTop(view)
                    TextViewCompat.setFirstBaselineToTopHeight(view as TextView, item.getTextBaseline(height, 1, 1))
                }
                is TextStackItem ->
                {
                    cs.alignParentTop(view)
                    item.stack.forEachIndexed { index, textItem ->
                        TextViewCompat.setFirstBaselineToTopHeight(
                                (view as ViewGroup).getChildAt(index) as TextView,
                                textItem.getTextBaseline(height, index, item.stack.size)
                        )
                    }
                }
                else -> cs.alignParentTop(view, item.getTopPadding(height))
            }
        }
    }
}