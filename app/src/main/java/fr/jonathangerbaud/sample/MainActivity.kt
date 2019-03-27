package fr.jonathangerbaud.sample

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.recyclerview.items.SingleLineItem
import fr.jonathangerbaud.ui.core.MaterialColor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val a = ResUtils.getDpInPx(12)
        val c = MaterialColor.AMBER_100

        val layout = findViewById<LinearLayout>(R.id.layout)
        layout.addView(SingleLineItem.build(this))
        layout.addView(SingleLineItem.build(this))
    }
}
