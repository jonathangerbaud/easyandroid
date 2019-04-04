package fr.jonathangerbaud.unsplash.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.jonathangerbaud.core.CoreDevice
import fr.jonathangerbaud.core.ext.d
import fr.jonathangerbaud.core.ext.getViewModel
import fr.jonathangerbaud.ui.core.AspectRatio
import fr.jonathangerbaud.ui.core.ToolbarDelegate
import fr.jonathangerbaud.ui.image.SuperImageView
import fr.jonathangerbaud.ui.recyclerview.RendererAdapter
import fr.jonathangerbaud.ui.recyclerview.ViewRenderer
import fr.jonathangerbaud.ui.recyclerview.decoration.Divider
import fr.jonathangerbaud.ui.state.DataLoaderDelegate
import fr.jonathangerbaud.ui.state.UIStateManager
import fr.jonathangerbaud.ui.state.widget.DataStateView
import fr.jonathangerbaud.unsplash.R
import fr.jonathangerbaud.unsplash.ui.main.model.Photo

class MainFragment : Fragment(), DataLoaderDelegate.DataLoaderCallback<List<Photo>>
{
    companion object
    {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataStateView: DataStateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)

        dataStateView = view.findViewById(R.id.dataStateView)
        //dataStateView.setEmptyMessage(a.emptyMessage)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        viewModel = getViewModel()
        ToolbarDelegate(activity as AppCompatActivity?, view!!.findViewById(R.id.toolbar)).title("Hello")

        recyclerView.layoutManager = LinearLayoutManager(activity)
        val divider = Divider().apply { setDividerSizeDp(1) }
        recyclerView.addItemDecoration(divider)
        val adapter = RendererAdapter()
        adapter.addView(Photo::class) { parent:ViewGroup -> PhotoRenderer(parent) }
        recyclerView.adapter = adapter

        DataLoaderDelegate(this, UIStateManager(recyclerView, dataStateView)) { Repository().getCuratedPhotos() }
    }

    override fun onDataLoaded(data: List<Photo>?)
    {
//        d("onDataLoaded $data")
        (recyclerView.adapter as RendererAdapter).data.addAll(data!!)
    }

    class PhotoRenderer(parent:ViewGroup) : ViewRenderer<Photo, SuperImageView>(SuperImageView(parent.context))
    {
        init {
            view.setAspectRatio(AspectRatio.x16_9)
            view.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            view.setBackgroundColor(0xFFFF0000.toInt())
        }

        override fun bind(data: Photo, position: Int)
        {
           Picasso.with(view.context)
                .load(data.urls.regular)
                .resize(CoreDevice.screenWidthPx, (CoreDevice.screenWidthPx / AspectRatio.x16_9).toInt())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(view)
        }
    }
}
