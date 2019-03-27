package fr.jonathangerbaud.ui.state

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fr.jonathangerbaud.ui.state.widget.DataStateView
import fr.jonathangerbaud.ui.core.ext.ViewVisibility.Companion.hide
import fr.jonathangerbaud.ui.core.ext.ViewVisibility.Companion.show

import java.lang.ref.WeakReference

class UIStateManager @JvmOverloads constructor(dataView: View?, dataStateView: DataStateView, extraView: View? = null)
{

    private val dataView: WeakReference<View> = WeakReference<View>(dataView)
    private val dataStateView: WeakReference<DataStateView> = WeakReference(dataStateView)
    private val extraView: WeakReference<View> = WeakReference<View>(extraView)

    private val isSRL: Boolean = dataView != null && dataView is SwipeRefreshLayout

    enum class State
    {
        DATA,
        LOADING,
        LOADING_FIRST,
        EMPTY,
        ERROR,
        EXTRA
    }

    fun setState(state: State)
    {
        dataView.get()?.let {
            if (state == State.DATA || state == State.LOADING && isSRL)
            {
                show()

                if (isSRL)
                    (it as SwipeRefreshLayout).isRefreshing = state == State.LOADING
            }
            else
            {
                hide()
            }
        }

       dataStateView.get()?.let {
            if (state == State.DATA || state == State.EXTRA || state == State.LOADING && isSRL)
            {
                hide()
            }
            else
            {
                show()

                if (state == State.LOADING || state == State.LOADING_FIRST)
                    it.setStateLoading()
                else if (state == State.EMPTY)
                    it.setStateEmpty()
                else if (state == State.ERROR)
                    it.setStateError()
            }
        }

        extraView.get()?.let {
            if (state == State.EXTRA) show() else hide()
        }
    }

    fun getDataView(): View?
    {
        return dataView.get()

    }

    fun getDataStateView(): DataStateView?
    {
        return dataStateView.get()

    }

    fun getExtraView(): View?
    {
        return extraView.get()

    }
}