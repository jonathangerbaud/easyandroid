package fr.jonathangerbaud.sample

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.jonathangerbaud.core.util.ResUtils
import fr.jonathangerbaud.ui.core.text.TextAppearance
import fr.jonathangerbaud.ui.image.MaskOptions
import fr.jonathangerbaud.ui.image.SuperImageView
import fr.jonathangerbaud.ui.image.PathHelper
import fr.jonathangerbaud.ui.listitems.Row
import fr.jonathangerbaud.ui.listitems.widgets.*

class MainActivity2 : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val image = findViewById<SuperImageView>(R.id.image)

        image.setMaskOptions(
            MaskOptions.Builder(
                PathHelper.roundedRect(
                    ResUtils.getDpInPx(256).toFloat(),
                    ResUtils.getDpInPx(256).toFloat(),
                    0f,
                    ResUtils.getDpInPx(40).toFloat(),
                    0f,
                    ResUtils.getDpInPx(80).toFloat()
                )
            ).build()
        )

//        image.setMaskBorderColor(0xFFFF0000.toInt())
//        image.setMaskBorderWidth(ResUtils.getDpInPx(15).toFloat())
//        image.setMaskBorderJoin(Paint.Join.ROUND)

        val layout = findViewById<LinearLayout>(R.id.layout)
        layout.setBackgroundColor(0xffcccccc.toInt())

        val customText = findViewById<TextView>(R.id.customText)
        TextAppearance.fromStyle(this, R.style.CustomTextAppearance).apply(customText)
        /*val v1 = SingleLineItem(this)
        val v2 = SingleLineItem(this)

        v1.setBackgroundColor(0xff00ff00.toInt())
        v1.setBackgroundColor(0xff0000ff.toInt())

        layout.addView(v1)
        layout.addView(v2)*/

        layout.addView(
            Row.Builder()
                .mainItem(HeadlineItem().text("Headline"))
                .build(this)
        )

        layout.addView(
            Row.Builder()
                .startItem(SmallIconItem().drawable(R.drawable.abc_ic_go_search_api_material))
                .mainItem(TitleItem().text("Title"))
                .endItem(CheckboxItem())
                .build(this)
        )

        layout.addView(
            Row.Builder()
                .startItem(SmallIconItem().drawable(R.drawable.abc_ic_go_search_api_material))
                .mainItem(TextBodyItem().text("Text Body"))
                .endItem(SwitchItem())
                .build(this)
        )

        layout.addView(
            Row.Builder()
                .startItem(IconItem().drawable(R.drawable.jg_ic_arrow_left).background(R.color.md_green400))
                .mainItem(OverlineItem().text("Overline"))
                .endItem(SwitchItem())
                .build(this)
        )

        layout.addView(
            Row.Builder()
                .startItem(SmallIconItem().drawable(R.drawable.ic_launcher_foreground).backgroundRes(R.color.colorPrimary))
                .mainItem(CaptionItem().text("My super caption"))
                .endItem(CheckboxItem())
                .build(this)
        )

        layout.addView(
            Row.Builder()
                .startItem(SmallIconItem().drawable(R.drawable.ic_launcher_foreground).backgroundRes(R.color.colorPrimary))
                .mainItem(MetaTextItem().text("Meta text"))
                .endItem(CheckboxItem())
                .build(this)
        )

        layout.addView(
            Row.Builder()
                .startItem(SmallIconItem().drawable(R.drawable.ic_launcher_foreground).backgroundRes(R.color.colorPrimary))
                .mainItem(
                    TextStackItem()
                        .addText(TitleItem().text("Three-line text"))
                        .addText(TextBodyItem().text("Secondary line text. Lorem ipsum dolor sit amet"))
                )
                .endItem(CheckboxItem())
                .build(this)
        )

        layout.addView(
            Row.Builder()
                .startItem(SmallIconItem().drawable(R.drawable.ic_launcher_foreground).backgroundRes(R.color.colorPrimary))
                .mainItem(
                    TextStackItem()
                        .addText(OverlineItem().text("Overline"))
                        .addText(TitleItem().text("Three-line text"))
                        .addText(TextBodyItem().text("Secondary line text. Lorem ipsum"))
                )
                .endItem(CheckboxItem())
                .build(this)
        )
    }
}
