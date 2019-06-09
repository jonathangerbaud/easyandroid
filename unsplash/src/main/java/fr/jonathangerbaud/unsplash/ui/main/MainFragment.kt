package fr.jonathangerbaud.unsplash.ui.main

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.ViewGroup.LayoutParams
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import fr.jonathangerbaud.core.util.Dimens
import fr.jonathangerbaud.ui.core.AspectRatio
import fr.jonathangerbaud.ui.appcompat.ToolbarDelegate
import fr.jonathangerbaud.ui.core.ext.setPadding
import fr.jonathangerbaud.ui.image.SuperImageView
import fr.jonathangerbaud.ui.listitems.Row
import fr.jonathangerbaud.ui.listitems.widgets.GridSubheaderItem
import fr.jonathangerbaud.ui.recyclerview.PagingDataLoaderDelegate
import fr.jonathangerbaud.ui.recyclerview.RendererAdapter
import fr.jonathangerbaud.ui.recyclerview.RowRenderer
import fr.jonathangerbaud.ui.recyclerview.ViewRenderer
import fr.jonathangerbaud.ui.recyclerview.decoration.*
import fr.jonathangerbaud.ui.state.UIStateManager
import fr.jonathangerbaud.ui.state.widget.DataStateView
import fr.jonathangerbaud.unsplash.R
import fr.jonathangerbaud.unsplash.ui.main.model.Photo

class MainFragment : Fragment(), PagingDataLoaderDelegate.PaginationDataLoaderCallback<List<Photo>>
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

    private lateinit var adapter:RendererAdapter
    private lateinit var loaderDelegate: PagingDataLoaderDelegate<List<Photo>>

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
        divider = GridDivider(Dimens.dp(4), false)
        divider.excludeViewItemsOfType(Row::class)
        divider.excludeDataItemsOfType(Subheader::class)

            //layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int
            {
                val data = adapter.getDataAtPosition(position)

                data?.let {
                    if (it is Subheader)
                        return layoutManager.spanCount
                }

                return 1
            }
        }

        adapter = RendererAdapter()
        adapter.addRenderer(::PhotoRenderer)
        adapter.addRenderer(::SubheaderRenderer)

        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(divider)
        recyclerView.adapter = adapter

        loaderDelegate = PagingDataLoaderDelegate(this, UIStateManager(srl, dataStateView), adapter) { nextPageData ->  Repository().getCuratedPhotos(nextPageData.page + 1, 20) }
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
            divider = GridDivider(Dimens.dp(spacing), false)
            //divider.excludeViewItemsOfType(Row::class)
            recyclerView.addItemDecoration(divider)

            if (MainFragment.aspectRatio != aspectRatio)
            {
                MainFragment.aspectRatio = aspectRatio
                recyclerView.adapter!!.notifyDataSetChanged()
            }
        }

        fragment.show(activity!!.supportFragmentManager, "BottomSheetDialog")
    }

    override fun onDataLoaded(data: List<Photo>?, isReset:Boolean)
    {
        if (isReset)
            (recyclerView.adapter as RendererAdapter).clear()

        adapter.add(Subheader("Unsplash Page ${loaderDelegate.nextPageData.page}"))
        adapter.addAll(data!!)
    }

    data class Subheader(val title:String)

    class SubheaderRenderer(parent: ViewGroup) : RowRenderer<Subheader>(parent)
    {
        private val title: TextView

        init {
            Row.Composer()
                .mainItem(GridSubheaderItem())
                .build(view)

            title = view.mainContent as TextView
            (view.layoutParams as ViewGroup.MarginLayoutParams).topMargin  = Dimens.dp(16)
//            view.setPaddingTop(Dimens.dp(16))
        }

        override fun bind(data: Subheader, position: Int)
        {
            title.text = data.title
        }
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
                view.doOnNextLayout { loadPhoto(data) }
        }

        private fun loadPhoto(data: Photo)
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
