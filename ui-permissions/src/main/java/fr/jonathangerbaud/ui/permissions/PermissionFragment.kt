package fr.jonathangerbaud.ui.permissions


import android.content.pm.PackageManager
import android.util.Log
import androidx.fragment.app.Fragment
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue


internal class PermissionFragment : Fragment()
{
    private val permissionQueue = ConcurrentLinkedQueue<PermissionHolder>()
    private var listener: PermissionListener? = null
    private var waitingForReceive = false
    private var requestCode:Int = 0

    init
    {
        retainInstance = true
    }

    override fun onResume()
    {
        super.onResume()
        runQueuePermission()
    }

    private fun runQueuePermission()
    {
        if (waitingForReceive)
            return

        val poll = permissionQueue.poll()

        if (poll != null)
        {
            waitingForReceive = true
            this.listener = poll.listener
            this.requestCode = poll.requestCode
            requestPermissions(poll.permissions.toTypedArray(), requestCode)
        }
        else
        {
            if (!waitingForReceive) removeFragment()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    {
        if (requestCode == requestCode && permissions.isNotEmpty() && grantResults.isNotEmpty())
        {

            val granted = ArrayList<String>()
            val askAgain = ArrayList<String>()
            val denied = ArrayList<String>()

            for (i in permissions.indices)
            {
                val permissionName = permissions[i]

                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                {
                    granted.add(permissionName)
                }
                else
                {
                    if (shouldShowRequestPermissionRationale(permissionName))
                        askAgain.add(permissionName)
                    else
                        denied.add(permissionName)
                }
            }

            listener?.onRequestPermissionsResult(granted, denied, askAgain)
        }

        waitingForReceive = false
    }

    private fun removeFragment()
    {
        try
        {
            fragmentManager?.beginTransaction()?.remove(this@PermissionFragment)?.commitAllowingStateLoss()
        }
        catch (e: Exception)
        {
            Log.w(TAG, "Error while removing fragment")
        }
    }

    internal fun addPermissionForRequest(listener: PermissionListener, permissions: List<String>, requestCode:Int)
    {
        permissionQueue.add(PermissionHolder(permissions, listener, requestCode))
    }

    /* Check whether any permission is denied before, if yes then we show a rationale dialog for explanation */
    private fun shouldShowRationale(vararg permissions: String): Boolean
    {
        for (permission in permissions)
        {
            if (shouldShowRequestPermissionRationale(permission))
                return true
        }

        return false
    }

    internal interface PermissionListener
    {
        fun onRequestPermissionsResult(granted: List<String>, denied: List<String>, askAgain: List<String>)
    }

    private data class PermissionHolder(val permissions: List<String>, val listener: PermissionListener, val requestCode:Int)

    companion object
    {
        private const val REQUEST_CODE = 23000

        fun newInstance(): PermissionFragment
        {
            return PermissionFragment()
        }

        private const val TAG = "PermissionFragment"
    }
}