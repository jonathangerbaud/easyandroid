package fr.jonathangerbaud.ui.permissions


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import fr.jonathangerbaud.core.util.AndroidUtil
import java.lang.ref.WeakReference
import java.util.concurrent.Semaphore

class PermissionsDelegate private constructor(
    activity: FragmentActivity,
    private val permissions: List<String>,
    private val callback: ((PermissionResult) -> Unit)? = null,
    private val requestCode:Int,
    private val rationaleMessage:String? = null
)
{
    private val activityReference: WeakReference<FragmentActivity> = WeakReference(activity)
    private val listener = object : PermissionFragment.PermissionListener
    {
        override fun onRequestPermissionsResult(granted: List<String>, denied: List<String>, askAgain: List<String>)
        {
            onReceivedPermissionResult(granted, denied, askAgain)
        }
    }

    companion object
    {
        private val semaphore: Semaphore = Semaphore(1)
        private const val FRAGMENT_TAG = "PermissionsDelegateFragment"

        const val READ_CALENDAR = Manifest.permission.READ_CALENDAR
        const val WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR
        const val CAMERA = Manifest.permission.CAMERA
        const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
        const val WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS
        const val GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS
        const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        const val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO
        const val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
        const val CALL_PHONE = Manifest.permission.CALL_PHONE
        const val READ_CALL_LOG = Manifest.permission.READ_CALL_LOG
        const val WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG
        const val ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL
        const val USE_SIP = Manifest.permission.USE_SIP
        const val PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS
        const val BODY_SENSORS = Manifest.permission.BODY_SENSORS
        const val SEND_SMS = Manifest.permission.SEND_SMS
        const val RECEIVE_SMS = Manifest.permission.RECEIVE_SMS
        const val READ_SMS = Manifest.permission.READ_SMS
        const val RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH
        const val RECEIVE_MMS = Manifest.permission.RECEIVE_MMS
        const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        const val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

        fun with(activity: FragmentActivity): Builder
        {
            return Builder(activity)
        }
    }

    class Builder internal constructor(private val activity: FragmentActivity, vararg permissions:String)
    {
        private val permissions:List<String> = permissions.toList()
        private var requestCode = randomCode()
        private var rationaleMessage:String? = null
        private var callback: ((PermissionResult) -> Unit)? = null

        private fun randomCode(): Int
        {
            return Math.round(Math.random() * 10).toInt() +
                    Math.round(Math.random() * 100).toInt() +
                    Math.round(Math.random() * 1000).toInt() +
                    Math.round(Math.random() * 10000).toInt()
        }

        fun listener(listener: (PermissionResult) -> Unit)
        {
            this.callback = listener
        }

        fun requestCode(code:Int): Builder
        {
            requestCode = code
            return this
        }

        fun rationaleMessage(message:String):Builder
        {
            rationaleMessage = message
            return this
        }

        fun build():PermissionsDelegate
        {
            return PermissionsDelegate(activity, permissions, callback, requestCode, rationaleMessage)
        }
    }

    fun checkPermissions()
    {
        semaphore.acquire()

        activityReference.get()?.let {
            if (!it.isFinishing)
            {
                //ne need < Android Marshmallow
                if (permissions.isEmpty()
                    || !AndroidUtil.isMinMarshmallow()
                    || permissionAlreadyAccepted(it, permissions)
                )
                {
                    onReceivedPermissionResult(permissions, emptyList(), emptyList())
                    semaphore.release()
                }
                else
                {
                    val oldFragment = it.supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as PermissionFragment?

                    if (oldFragment != null)
                    {
                        oldFragment.addPermissionForRequest(listener, permissions, requestCode)
                        semaphore.release()
                    }
                    else
                    {
                        val newFragment = PermissionFragment.newInstance()
                        newFragment.addPermissionForRequest(listener, permissions, requestCode)
                        Try.withThreadIfFail({
                            it.runOnUiThread {
                                it.supportFragmentManager
                                    .beginTransaction()
                                    .add(newFragment, FRAGMENT_TAG)
                                    .commitNowAllowingStateLoss()
                                semaphore.release()
                            }
                        }, 3)
                    }
                }
            }
            else
            {
                semaphore.release()
            }
        }
    }

    internal fun onReceivedPermissionResult(granted: List<String>, foreverDenied: List<String>, askAgain: List<String>)
    {
        callback?.invoke(PermissionResult(granted, foreverDenied, askAgain))
    }

    private fun permissionAlreadyAccepted(context: Context, permissions: List<String>): Boolean
    {
        for (permission in permissions)
        {
            val permissionState = ContextCompat.checkSelfPermission(context, permission)
            if (permissionState == PackageManager.PERMISSION_DENIED)
                return false
        }

        return true
    }
}
