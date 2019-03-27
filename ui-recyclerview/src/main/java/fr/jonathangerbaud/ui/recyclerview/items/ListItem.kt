package fr.jonathangerbaud.ui.recyclerview.items

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import fr.jonathangerbaud.ui.recyclerview.R
import fr.jonathangerbaud.ui.recyclerview.ext.*


open class ListItem : ConstraintLayout
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

    class Builder
    {
        private var startBuilder: WidgetBuilder? = null
        private var mainBuilder: WidgetBuilder? = null
        private var endBuilder: WidgetBuilder? = null

        fun startContent(builder: WidgetBuilder): Builder
        {
            startBuilder = builder
            return this
        }

        fun mainContent(builder: WidgetBuilder): Builder
        {
            this.mainBuilder = builder
            return this
        }

        fun endContent(builder: WidgetBuilder): Builder
        {
            endBuilder = builder
            return this
        }

        fun build(context: Context): ListItem
        {
            val view = ListItem(context)

            val startItem: View? = startBuilder?.buildView(context)
            val mainItem: View? = mainBuilder?.buildView(context)
            val endItem: View? = endBuilder?.buildView(context)

            // Define min height
            var height = 48

            startItem?.run { height = Math.min(height, startBuilder!!.measurements.getMinListItemHeight()) }
            endItem?.run { height = Math.min(height, endBuilder!!.measurements.getMinListItemHeight()) }

            startItem?.apply { id = R.id.startContent }
            mainItem?.apply { id = R.id.mainContent }
            endItem?.apply { id = R.id.endContent }

            // Define constraints
            val cs = ConstraintSet()

            startItem?.let {
                cs.alignStartParentStart(it, startBuilder!!.measurements.getStartMarginIfStartComponent())

                val gravity = startBuilder!!.measurements.getVerticalGravity()
                if (gravity == Gravity.CENTER_VERTICAL || gravity == Gravity.CENTER)
                    cs.centerInParentVertically(it)
                else if (gravity == Gravity.BOTTOM)
                    cs.alignParentTop(it, startBuilder!!.measurements.getTopPadding(height))
                else
                    cs.alignParentBottom(it, startBuilder!!.measurements.getTopPadding(height))
            }

            mainItem?.let {
                cs.alignParentTop(mainItem, mainBuilder!!.measurements.getTopPadding(height))

                if (startItem != null)
                    cs.alignStartToEnd(startItem, mainItem, startBuilder!!.measurements.getEndMargin())
                else
                    cs.alignStartParentStart(mainItem, 16) // ToDo set constant for default padding

                if (endItem != null)
                    cs.alignEndToStart(mainItem, startItem!!, mainBuilder!!.measurements.getEndMargin())
                else
                    cs.alignEndParentEnd(mainItem, mainBuilder!!.measurements.getEndMarginIfEndComponent())
            }

            endItem?.let {
                cs.alignEndParentEnd(it, endBuilder!!.measurements.getEndMarginIfEndComponent())

                val gravity = endBuilder!!.measurements.getVerticalGravity()
                if (gravity == Gravity.CENTER_VERTICAL || gravity == Gravity.CENTER)
                    cs.centerInParentVertically(it)
                else if (gravity == Gravity.BOTTOM)
                    cs.alignParentTop(it, endBuilder!!.measurements.getTopPadding(height))
                else
                    cs.alignParentBottom(it, endBuilder!!.measurements.getTopPadding(height))

                cs.constrainWidth(it.id, ConstraintSet.MATCH_CONSTRAINT_WRAP)
                cs.constrainHeight(it.id, ConstraintSet.WRAP_CONTENT)
            }

            view.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            view.minimumHeight = height

            // Add children
            startItem?.let { view.addView(startItem) }
            mainItem?.let { view.addView(mainItem) }
            endItem?.let { view.addView(startItem) }

            view.startContent = startItem
            view.mainContent = mainItem
            view.endContent = endItem

            return view
        }
    }
}