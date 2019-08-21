package fr.jonathangerbaud.ui.recyclerview

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import fr.jonathangerbaud.core.ext.d


class PaginationDelegate(recyclerView: RecyclerView, callback: ((page: Int) -> Unit)? = null)
{
    var visibleThreshold = 5
    var currentPage = 0

    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 0

    private val callbacks: ArrayList<(page: Int) -> Unit> = arrayListOf()

    init
    {
        callback?.let { callbacks.add(it) }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
            {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager
                var threshold = visibleThreshold

                // If the recycler view is scrolling, the layoutManager shouldn't be null
                // but prevention is better than cure
                layoutManager?.let {
                    var lastVisibleItemPosition = 0
                    val totalItemCount = layoutManager.itemCount

                    if (layoutManager is StaggeredGridLayoutManager)
                    {
                        threshold *= layoutManager.spanCount
                        val lastVisibleItemPositions =
                            (layoutManager).findLastVisibleItemPositions(null)
                        lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
                    }
                    else if (layoutManager is GridLayoutManager)
                    {
                        threshold *= layoutManager.spanCount
                        lastVisibleItemPosition = (layoutManager).findLastVisibleItemPosition()
                    }
                    else if (layoutManager is LinearLayoutManager)
                    {
                        lastVisibleItemPosition = (layoutManager).findLastVisibleItemPosition()
                    }

                    if (totalItemCount < previousTotalItemCount)
                    {
                        currentPage = startingPageIndex
                        previousTotalItemCount = totalItemCount

                        if (totalItemCount == 0)
                            loading = true
                    }

                    // We are most likely going to add an item to show the loading state of the list
                    // so the previousTotalItemCount will be useless if we don't increment it by 1
                    // Else this delegate will send callback events indefinitely
                    // We don't take much risk here, as it would be rare to load the content
                    // one by one
                    if (loading && totalItemCount > previousTotalItemCount + 1)
                    {
                        loading = false
                        previousTotalItemCount = totalItemCount
                    }

                    if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount)
                    {
                        currentPage++
                        loading = true
                        previousTotalItemCount = totalItemCount // initialize else it will be 0 and trigger send another next page shortly after
                        recyclerView.post { callbacks.forEach { it(currentPage) } }
                    }
                }

            }
        })
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int
    {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices)
        {
            if (i == 0)
            {
                maxSize = lastVisibleItemPositions[i]
            }
            else if (lastVisibleItemPositions[i] > maxSize)
            {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    fun addCallback(callback: (page: Int) -> Unit)
    {
        callbacks.add(callback)
    }

    fun removeCallback(callback: (page: Int) -> Unit)
    {
        callbacks.remove(callback)
    }

    fun reset()
    {
        loading = false
        currentPage = 0
        previousTotalItemCount = 0
    }
}