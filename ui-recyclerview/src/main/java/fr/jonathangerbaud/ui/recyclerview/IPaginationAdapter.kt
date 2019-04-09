package fr.jonathangerbaud.ui.recyclerview

import android.view.View
import androidx.annotation.LayoutRes


interface IPaginationAdapter
{
    fun setLoadingView(view: View) {}
    fun setLoadingView(@LayoutRes layoutRes: Int) {}
    fun setErrorView(view: View) {}
    fun setErrorView(@LayoutRes layoutRes: Int) {}
    fun setErrorRetryCallback(callback:() -> Unit)

    fun setPaginationDelegate(paginationDelegate: PaginationDelegate) {}

    fun showLoading(loading:Boolean = true)
    fun showError()
}