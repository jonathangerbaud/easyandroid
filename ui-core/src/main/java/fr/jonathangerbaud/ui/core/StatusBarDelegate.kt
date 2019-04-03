package fr.jonathangerbaud.ui.core

import android.app.Activity
import android.os.Build
import android.view.View
import java.lang.ref.WeakReference


class StatusBarDelegate(activity: Activity)
{
    private val activityRef = WeakReference(activity)

    private fun setStatusBarDark(dark: Boolean)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            var flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

            if (dark)
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            activityRef.get()?.window?.decorView?.systemUiVisibility = flags
        }
    }
}