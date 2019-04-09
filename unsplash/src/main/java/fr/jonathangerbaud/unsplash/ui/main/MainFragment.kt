package fr.jonathangerbaud.unsplash.ui.main

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import fr.jonathangerbaud.core.CoreDevice
import fr.jonathangerbaud.core.ext.d
import fr.jonathangerbaud.core.ext.getViewModel
import fr.jonathangerbaud.core.util.Dimens
import fr.jonathangerbaud.network.Resource
import fr.jonathangerbaud.ui.core.AspectRatio
import fr.jonathangerbaud.ui.core.ToolbarDelegate
import fr.jonathangerbaud.ui.core.ext.setHeight
import fr.jonathangerbaud.ui.core.ext.setPadding
import fr.jonathangerbaud.ui.image.SuperImageView
import fr.jonathangerbaud.ui.recyclerview.PaginationDelegate
import fr.jonathangerbaud.ui.recyclerview.RendererAdapter
import fr.jonathangerbaud.ui.recyclerview.ViewRenderer
import fr.jonathangerbaud.ui.recyclerview.decoration.*
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
        var aspectRatio = AspectRatio.SQUARE
    }

    private lateinit var srl: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataStateView: DataStateView

    private lateinit var layoutManager: GridLayoutManager
    private lateinit var divider: GridDivider

    init
    {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)

        dataStateView = view.findViewById(R.id.dataStateView)
        //dataStateView.setEmptyMessage(a.emptyMessage)

        srl = view.findViewById(R.id.swipe_refresh_layout)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        ToolbarDelegate(activity as AppCompatActivity?, view!!.findViewById(R.id.toolbar)).title("Hello")

        layoutManager = GridLayoutManager(activity, 2)
        divider = GridDivider(Dimens.dp(4), 2, false)

        val adapter = RendererAdapter()
        adapter.addView(Photo::class, ::PhotoRenderer)

        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(divider)
        recyclerView.adapter = adapter

        // page starts at 0 while API starts at 1
        PaginationDelegate(recyclerView) { page ->
            Repository().getCuratedPhotos(page + 1, 20).observe(this, Observer<Resource<List<Photo>>> {
                when (it.status)
                {
                    Resource.LOADING -> adapter.setLoading()
                    Resource.ERROR -> adapter.setError()
                    Resource.SUCCESS ->
                    {
                        adapter.setLoading(false)
                        adapter.addAll(it.data!!)
                    }
                }
            })
        }

        DataLoaderDelegate(this, UIStateManager(srl, dataStateView)) { Repository().getCuratedPhotos(1, 20) }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        item?.let {
            if (it.itemId == R.id.menu_customize)
            {
                showBottomSheetDialog()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showBottomSheetDialog()
    {
        val fragment = CustomBottomSheetFragment(
                layoutManager.spanCount,
                Dimens.pxToDp(recyclerView.paddingStart),
                Dimens.pxToDp(divider.spacingPx),
                MainFragment.aspectRatio)

        fragment.callback = { columns, padding, spacing, aspectRatio ->
            layoutManager.spanCount = columns
            recyclerView.setPadding(Dimens.dp(padding))

            recyclerView.removeItemDecoration(divider)
            divider = GridDivider(Dimens.dp(spacing), columns, false)
            recyclerView.addItemDecoration(divider)

            if (MainFragment.aspectRatio != aspectRatio)
            {
                MainFragment.aspectRatio = aspectRatio
                recyclerView.adapter!!.notifyDataSetChanged()
            }
        }

        fragment.show(activity!!.supportFragmentManager, "BottomSheetDialog")
    }

    override fun onDataLoaded(data: List<Photo>?)
    {
        (recyclerView.adapter as RendererAdapter).data.addAll(data!!)
    }

    class PhotoRenderer(parent: ViewGroup) : ViewRenderer<Photo, SuperImageView>(SuperImageView(parent.context))
    {
        private val drawable = ColorDrawable(0xFFDDDDDD.toInt())
        private var aspectRatio = MainFragment.aspectRatio

        init
        {
            view.setAspectRatio(aspectRatio)
            view.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        }

        override fun bind(data: Photo, position: Int)
        {
            if (MainFragment.aspectRatio != aspectRatio)
            {
                aspectRatio = MainFragment.aspectRatio
                view.setAspectRatio(MainFragment.aspectRatio)
                view.post { loadPhoto(data) }
            }
            else if (view.width > 0)
                loadPhoto(data)
            else
                view.post { loadPhoto(data) }
        }

        fun loadPhoto(data: Photo)
        {
            Picasso.with(view.context)
                .cancelRequest(view)
            Picasso.with(view.context)
                .load("${data.urls.raw}&w=${view.width}")
                .resize(view.width, view.height)
                .centerCrop()
                .placeholder(drawable)
                .noFade()
//                .noPlaceholder()
                .into(view)
        }
    }
}
