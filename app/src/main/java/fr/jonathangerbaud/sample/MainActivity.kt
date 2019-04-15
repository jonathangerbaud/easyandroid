package fr.jonathangerbaud.sample

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.jonathangerbaud.ui.listitems.Row
import fr.jonathangerbaud.ui.listitems.widgets.*
import fr.jonathangerbaud.ui.recyclerview.*
import fr.jonathangerbaud.ui.recyclerview.decoration.Divider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val divider = Divider()
        divider.setDividerSizeDp(8)
        divider.range = IntRange(0, 10)
        divider.setColor(0xFFFF0000.toInt())
        divider.setMarginsRes(R.dimen.dp_16)
        //divider.showOnlyBetweenViewsOfType(Row::class)
        //divider.showOnlyBetweenDataOfType(BasicItem::class)
        //recyclerView.addItemDecoration(divider)

        val adapter = RendererAdapter()
        adapter.addRenderer(::BasicRowRenderer)
        adapter.addRenderer(::BasicRowRenderer2)
        adapter.addRenderer(::HeaderRenderer)
        recyclerView.adapter = adapter

        val data = arrayListOf<Any>()
        data.add(HeaderItem("My super header"))
        data.add(BasicItem("Hello there"))
        data.add(BasicItem("How are you doing?"))
        data.add(BasicItem("01234567489"))
        data.add(BasicItem("Hello there"))
        data.add(BasicItem("How are you doing?"))
        data.add(BasicItem("01234567489"))
        data.add(BasicItem("Hello there"))
        data.add(HeaderItem("Another category"))
        data.add(BasicItem("How are you doing?"))
        data.add(BasicItem("01234567489"))
        data.add(BasicItem("Hello there"))
        data.add(BasicItem("How are you doing?"))
        data.add(BasicItem("01234567489"))
        data.add(BasicItem("Hello there"))
        data.add(BasicItem("How are you doing?"))
        data.add(BasicItem("01234567489"))
        data.add(BasicItem("Hello there"))
        data.add(BasicItem("How are you doing?"))
        data.add(BasicItem("01234567489"))
        data.add(BasicItem2("Hello there"))
        data.add(BasicItem("How are you doing?"))
        data.add(BasicItem("01234567489"))
        adapter.addAll(data)
    }

    class BasicItem(val name:String)
    class BasicItem2(val name:String)
    class HeaderItem(val title:String)

    class BasicRowRenderer(parent:ViewGroup) : RowRenderer<BasicItem>(parent)
    {
        val title:TextView

        init {
            Row.Composer()
                .mainItem(TitleItem { text = "Initial text" })
                .build(view)

            title = view.mainContent as TextView
        }

        override fun bind(data: BasicItem, position: Int)
        {

        }
    }

    class BasicRowRenderer2(parent:ViewGroup) : Renderer<BasicItem2>(parent, R.layout.item_simple)
    {
        val textView:TextView = itemView.findViewById(R.id.title)

        override fun bind(data: BasicItem2, position: Int)
        {
            textView.text = data.name
        }
    }

    class HeaderRenderer(parent:ViewGroup) : RowRenderer<HeaderItem>(parent)
    {
        val title:TextView

        init {
            Row.Composer()
                .mainItem(SubheaderItem { text = "Initial text" })
                .build(view)

            title = view.mainContent as TextView
        }

        override fun bind(data: HeaderItem, position: Int)
        {
            title.text = data.title
        }
    }
}
