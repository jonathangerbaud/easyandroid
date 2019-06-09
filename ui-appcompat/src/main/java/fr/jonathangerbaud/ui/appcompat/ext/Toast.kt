package fr.jonathangerbaud.ui.appcompat.ext

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment?.toast(msg: String) = makeToast(this?.context, msg, Toast.LENGTH_LONG)
fun Fragment?.shortToast(msg: String) = makeToast(this?.context, msg, Toast.LENGTH_SHORT)
fun Fragment?.longToast(msg: String) = makeToast(this?.context, msg, Toast.LENGTH_LONG)
fun Fragment?.toastNow(msg: String) = cancelAndMakeToast(this?.context, msg, Toast.LENGTH_LONG)
fun Fragment?.shortToastNow(msg: String) = cancelAndMakeToast(this?.context, msg, Toast.LENGTH_SHORT)
fun Fragment?.longToastNow(msg: String) = cancelAndMakeToast(this?.context, msg, Toast.LENGTH_LONG)


fun cancelAllToasts() = ToastQueue.cancelToasts()

private fun cancelAndMakeToast(ctx: Context?, msg: String, duration: Int): Toast? {
    ToastQueue.cancelToasts()
    return makeToast(ctx, msg, duration)
}

private fun makeToast(ctx: Context?, msg: String, duration: Int): Toast? {
    return ctx?.let {
        val toast = Toast.makeText(ctx, msg, duration)
        toast.show()
        // remove from list after 4 seconds (longest toast time is 3.5 seconds)
        toast.view?.postDelayed({
            ToastQueue.removeToast(toast)
        }, 4000L)
        ToastQueue.toastQueue.add(toast)
        toast
    }
}

private object ToastQueue {
    val toastQueue = mutableListOf<Toast>()

    fun cancelToasts() {
        toastQueue.forEach { it.cancel() }
        toastQueue.clear()
    }

    fun removeToast(toast: Toast) = toastQueue.remove(toast)

}