package fr.jonathangerbaud.ui.state

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fr.jonathangerbaud.core.ext.d
import fr.jonathangerbaud.ui.core.ext.hide
import fr.jonathangerbaud.ui.core.ext.show
import fr.jonathangerbaud.ui.state.widget.DataStateView

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
        d("setState $state")
        dataView.get()?.let {
            if (state == State.DATA || state == State.LOADING && isSRL)
            {
                d("show recyclerview")
                it.show()

                if (isSRL)
                    (it as SwipeRefreshLayout).isRefreshing = state == State.LOADING
            }
            else
            {
                it.hide()
            }
        }

       dataStateView.get()?.let {
            if (state == State.DATA || state == State.EXTRA || state == State.LOADING && isSRL)
            {
                d("hide datastateview")
                it.hide()
            }
            else
            {
                it.show()

                if (state == State.LOADING || state == State.LOADING_FIRST)
                    it.setStateLoading()
                else if (state == State.EMPTY)
                    it.setStateEmpty()
                else if (state == State.ERROR)
                    it.setStateError()
            }
        }

        extraView.get()?.let {
            if (state == State.EXTRA) it.show() else it.hide()
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