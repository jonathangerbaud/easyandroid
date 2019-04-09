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
        adapter.addView(BasicItem::class) { parent:ViewGroup -> BasicRowRenderer(parent)}
        adapter.addView(BasicItem2::class) { parent:ViewGroup -> BasicRowRenderer2(parent)}
        adapter.addView(HeaderItem::class) { parent:ViewGroup -> HeaderRenderer(parent)}
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


    class BasicRow(context: Context) : Row(context)
    {
        val title:TextView

        init {
            Builder()
                .mainItem(TitleItem().text("Initial text"))
                .build(context, this)

            title = mainContent as TextView
        }
    }

    class HeaderRow(context: Context) : Row(context)
    {
        val title:TextView

        init {
            Builder()
                .mainItem(InsetSubheaderItem().text("Initial text"))
                .build(context, this)

            title = mainContent as TextView
        }
    }

    class BasicRowRenderer(parent:ViewGroup) : ViewRenderer<BasicItem, BasicRow>(BasicRow(parent.context))
    {
        init {
            // do initilazing stuff on view
//            view.title
        }

        override fun bind(data: BasicItem, position: Int)
        {
            view.title.text = data.name
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

    class HeaderRenderer(parent:ViewGroup) : ViewRenderer<HeaderItem, HeaderRow>(HeaderRow(parent.context))
    {
        override fun bind(data: HeaderItem, position: Int)
        {
            view.title.text = data.title
        }
    }
}
