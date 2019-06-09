package fr.jonathangerbaud.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.jonathan.mockdata.MockData
import fr.jonathan.mockdata.MockData.Employee
import fr.jonathan.mockdata.MockServer
import fr.jonathangerbaud.ui.appcompat.ToolbarDelegate
import fr.jonathangerbaud.ui.listitems.Row
import fr.jonathangerbaud.ui.listitems.widgets.AvatarItem
import fr.jonathangerbaud.ui.listitems.widgets.TextBodyItem
import fr.jonathangerbaud.ui.listitems.widgets.TextStackItem
import fr.jonathangerbaud.ui.listitems.widgets.TitleItem
import fr.jonathangerbaud.ui.recyclerview.RendererAdapter
import fr.jonathangerbaud.ui.recyclerview.RowRenderer
import fr.jonathangerbaud.ui.state.DataLoaderDelegate
import fr.jonathangerbaud.ui.state.UIStateManager
import fr.jonathangerbaud.ui.state.widget.DataStateView

class MainFragment : Fragment(), DataLoaderDelegate.DataLoaderCallback<List<Employee>>
{
    companion object
    {
        fun newInstance() = MainFragment()
    }

    private lateinit var adapter: RendererAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        ToolbarDelegate(activity as AppCompatActivity, view.findViewById(R.id.toolbar))

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val stateView = view.findViewById<DataStateView>(R.id.dataStateView)

        adapter = RendererAdapter()
        //adapter.addView(Employee::class, ::EmployeeRenderer)
        adapter.addRenderer(::EmployeeRenderer)
        recyclerView.adapter = adapter

        DataLoaderDelegate(
                this,
                UIStateManager(
                        view.findViewById(R.id.swipe_refresh_layout),
                        stateView)) { MockServer().list(MockData::employee) }
    }

    override fun onDataLoaded(data: List<Employee>?)
    {
        adapter.addAll(data!!)
    }

    class EmployeeRenderer(parent: ViewGroup) : RowRenderer<Employee>(parent)
    {
        private val avatar: ImageView
        private val name: TextView
        private val job: TextView

        init
        {
            // do initializing stuff on view
//            view.title
            Row.Composer()
                .startItem(AvatarItem().circle())
                .mainItem(
                        TextStackItem()
                            .addText(TitleItem())
                            .addText(TextBodyItem()))
//                .mainItem(TitleItem())
                .build(view)

            avatar = view.startContent as ImageView
            name = (view.mainContent as ViewGroup).getChildAt(0) as TextView
            job = (view.mainContent as ViewGroup).getChildAt(1) as TextView
//            name = view.mainContent as TextView
        }

        override fun bind(data: Employee, position: Int)
        {
            Picasso.with(view.context)
                .load(data.avatarImageUrl)
                .into(avatar)

            name.text = "${data.firstName} ${data.lastName}"
            job.text = data.jobTitle

        }
    }
}
