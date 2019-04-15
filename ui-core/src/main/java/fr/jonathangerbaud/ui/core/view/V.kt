package fr.jonathangerbaud.ui.core.view

import android.content.Context
import android.content.ContextWrapper
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.*
import androidx.annotation.StyleRes

object V
{
    fun <V : View> view(
        context:Context,
        createView: (Context) -> V,
        initView: V.() -> Unit = {}
    ): V {
//        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return createView(context).apply(initView)
    }

    fun button(context:Context, initView:Button.() -> Unit = {}): Button =
        view(context, ::Button, initView)
    fun textView(context:Context, initView:TextView.() -> Unit = {}): TextView =
        view(context, ::TextView, initView)
    fun checkbox(context:Context, initView:CheckBox.() -> Unit = {}): CheckBox =
        view(context, ::CheckBox, initView)
    fun switch(context:Context, initView: Switch.() -> Unit = {}): Switch =
        view(context, ::Switch, initView)
    fun imageView(context:Context, initView: ImageView.() -> Unit = {}): ImageView =
        view(context, ::ImageView, initView)
    fun imageButton(context:Context, initView: ImageButton.() -> Unit = {}): ImageButton =
        view(context, ::ImageButton, initView)
    fun editText(context:Context, initView: EditText.() -> Unit = {}): EditText =
        view(context, ::EditText, initView)
    fun radioButton(context:Context, initView: RadioButton.() -> Unit = {}): RadioButton =
        view(context, ::RadioButton, initView)
    fun radioButtonGroup(context:Context, initView: RadioGroup.() -> Unit = {}): RadioGroup =
        view(context, ::RadioGroup, initView)
    fun frameLayout(context:Context, initView: FrameLayout.() -> Unit = {}): FrameLayout =
        view(context, ::FrameLayout, initView)
    fun hLinearLayout(context:Context, initView: LinearLayout.() -> Unit = {}): LinearLayout =
        view(context, ::LinearLayout) {
            orientation = LinearLayout.HORIZONTAL
            initView()
        }
    fun vLinearLayout(context:Context, initView: LinearLayout.() -> Unit = {}): LinearLayout =
        view(context, ::LinearLayout) {
            orientation = LinearLayout.VERTICAL
            initView()
        }

    /**
     * Returns a context for the theme in parameter
     */
    fun themedContext(context: Context, @StyleRes theme:Int):Context = ContextThemeWrapper(context, theme)
}