package fr.jonathangerbaud.sample

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.jonathangerbaud.core.CoreDevice
import fr.jonathangerbaud.core.ext.d
import fr.jonathangerbaud.core.util.Dimens
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

        d(CoreDevice.toString())

        val image = findViewById<SuperImageView>(R.id.image)

        image.setMaskOptions(
                MaskOptions.Builder(
                        PathHelper.roundedRect(
                                Dimens.dpF(256),
                                Dimens.dpF(256),
                                0f,
                                Dimens.dpF(40),
                                0f,
                                Dimens.dpF(80)
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

        layout.addView(Row.Composer().mainItem(HeadlineItem("Headline")).build(this))

        layout.addView(
                Row.Composer()
                    .startItem(SmallIconItem(R.drawable.abc_ic_go_search_api_material))
                    .mainItem(TitleItem("Title"))
                    .endItem(CheckboxItem())
                    .build(this)
        )

        layout.addView(
                Row.Composer()
                    .startItem(SmallIconItem(R.drawable.abc_ic_go_search_api_material))
                    .mainItem(TextBodyItem("Text Body"))
                    .endItem(SwitchItem())
                    .build(this)
        )

        layout.addView(
                Row.Composer()
                    .startItem(IconItem(R.drawable.jg_ic_arrow_left) { setBackgroundResource(R.color.md_green400) })
                    .mainItem(OverlineItem("Overline"))
                    .endItem(SwitchItem())
                    .build(this)
        )

        layout.addView(
                Row.Composer()
                    .startItem(SmallIconItem(R.drawable.ic_launcher_foreground) { setBackgroundResource(R.color.colorPrimary) })
                    .mainItem(CaptionItem("My super caption"))
                    .endItem(CheckboxItem())
                    .build(this)
        )

        layout.addView(
                Row.Composer()
                    .startItem(SmallIconItem(R.drawable.ic_launcher_foreground) { setBackgroundResource(R.color.colorPrimary) })
                    .mainItem(MetaTextItem("Meta text"))
                    .endItem(CheckboxItem())
                    .build(this)
        )

        layout.addView(
                Row.Composer()
                    .startItem(SmallIconItem(R.drawable.ic_launcher_foreground) { setBackgroundResource(R.color.colorPrimary) })
                    .mainItem(
                            TextStackItem()
                                .addText(TitleItem("Three-line text"))
                                .addText(TextBodyItem("Secondary line text. Lorem ipsum dolor sit amet"))
                    )
                    .endItem(CheckboxItem())
                    .build(this)
        )

        layout.addView(
                Row.Composer()
                    .startItem(SmallIconItem(R.drawable.ic_launcher_foreground) { setBackgroundResource(R.color.colorPrimary) })
                    .mainItem(
                            TextStackItem()
                                .addText(OverlineItem("Overline"))
                                .addText(TitleItem("Three-line text"))
                                .addText(TextBodyItem("Secondary line text. Lorem ipsum"))
                    )
                    .endItem(CheckboxItem())
                    .build(this)
        )
    }
}
