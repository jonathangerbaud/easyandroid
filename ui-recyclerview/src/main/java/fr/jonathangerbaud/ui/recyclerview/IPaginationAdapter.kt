package fr.jonathangerbaud.ui.recyclerview

import android.view.View
import androidx.annotation.LayoutRes


interface IPaginationAdapter
{
    fun setLoadingView(view: View) {}
    fun setLoadingView(@LayoutRes layoutRes: Int) {}
    fun setErrorView(view: View) {}
    fun setErrorView(@LayoutRes layoutRes: Int) {}

    fun setPaginationDelegate(paginationDelegate: PaginationDelegate) {}

    fun setLoading(loading:Boolean = true)
    fun setError()
}