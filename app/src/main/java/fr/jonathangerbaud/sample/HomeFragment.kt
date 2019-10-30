package fr.jonathangerbaud.sample

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import fr.jonathangerbaud.ui.core.ext.rippleForeground
import fr.jonathangerbaud.ui.listitems.Row
import fr.jonathangerbaud.ui.listitems.widgets.TitleItem
import kotlin.reflect.KClass


class HomeFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return LinearLayout(inflater.context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val layout = view as LinearLayout

        val composer = Row.Composer()
            .init {
                rippleForeground()
                setOnClickListener { start(layout.indexOfChild(this)) }
            }

        for (i in 0..2)
            layout.addView(composer.mainItem(TitleItem("Activity ${i + 1}")).build(view.context))
    }

    private fun start(index: Int)
    {
        when (index)
        {
            0 -> startActivity(Intent(context, MainActivity::class.java))
            1 -> startActivity(Intent(context, MainActivity2::class.java))
//            2 -> throw Exception("Where the fu** is my crash stacktrace????")
            2 -> startActivity(Intent(context, MainActivity3::class.java))
        }

    }
}