package fr.jonathangerbaud.ui.constraintlayout.ext

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet


fun ConstraintSet.alignParentTop(view: View, margin:Int = 0) = connect(view.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, margin)
fun ConstraintSet.alignParentBottom(view: View, margin:Int = 0) = connect(view.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, margin)
fun ConstraintSet.centerInParentVertically(view: View)
{
    alignParentTop(view)
    alignParentBottom(view)
}

fun ConstraintSet.alignStartParentStart(view:View, margin:Int = 0) = connect(view.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, margin)
fun ConstraintSet.alignEndParentEnd(view:View, margin:Int = 0) = connect(view.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, margin)
fun ConstraintSet.centerInParentHorizontally(view: View) {
    alignStartParentStart(view)
    alignEndParentEnd(view)
}
fun ConstraintSet.centerInParent(view: View)
{
    centerInParentHorizontally(view)
    centerInParentVertically(view)
}

fun ConstraintSet.alignEndToStart(endView: View, startView: View, margin: Int = 0) = connect(endView.id, ConstraintSet.END, startView.id, ConstraintSet.START, margin)
fun ConstraintSet.alignStartToEnd(endView: View, startView: View, margin: Int = 0) = connect(startView.id, ConstraintSet.START, endView.id, ConstraintSet.END, margin)