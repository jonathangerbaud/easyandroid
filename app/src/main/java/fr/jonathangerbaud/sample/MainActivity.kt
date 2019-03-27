package fr.jonathangerbaud.sample

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.core.MaterialColor
import fr.jonathangerbaud.ui.recyclerview.items.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val a = ResUtils.getDpInPx(12)
        val c = MaterialColor.AMBER_100

        val layout = findViewById<LinearLayout>(R.id.layout)
        layout.setBackgroundColor(0xffcccccc.toInt())

        /*val v1 = SingleLineItem(this)
        val v2 = SingleLineItem(this)

        v1.setBackgroundColor(0xff00ff00.toInt())
        v1.setBackgroundColor(0xff0000ff.toInt())

        layout.addView(v1)
        layout.addView(v2)*/

        layout.addView(
            ListItem.Builder()
                .startContent(SmallIconBuilder().drawable(fr.jonathangerbaud.ui.recyclerview.R.drawable.abc_ic_go_search_api_material))
                .mainContent(SingleLineBuilder().text("hello"))
                .endContent(SwitchBuilder())
                .build(this))

        layout.addView(
            ListItem.Builder()
                .startContent(SmallIconBuilder().drawable(R.drawable.ic_launcher_foreground).backgroundRes(R.color.colorPrimary))
                .mainContent(SingleLineBuilder().text("This is my super text"))
                .endContent(CheckboxBuilder())
                .build(this))
    }
}
