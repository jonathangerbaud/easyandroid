package fr.jonathangerbaud.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.location.Location
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import fr.jonathangerbaud.core.AppInstance
import java.lang.ref.WeakReference

class LocationDelegate(
    context: Activity,
    private val lifecycle: Lifecycle,
    private val callback: (Location) -> Unit
) : LifecycleObserver
{
    private val activity = WeakReference<Activity>(context)
    private var enabled = false
    private val locationCallback = object : LocationCallback()
    {
        override fun onLocationResult(locationResult: LocationResult?)
        {
            locationResult ?: return

            for (location in locationResult.locations)
                callback(location)
        }
    }
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    public val REQUEST_CHECK_SETTINGS: Int = 2569


    fun enable(enable: Boolean)
    {
        this.enabled = enable

        if (enable)
        {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
            {
                start()
            }
        }
        else
        {
            stop()
        }
    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun start()
    {
        if (enabled)
        {
            val locationRequest = LocationRequest()
                .setFastestInterval(5000)
                .setInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)


            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            val client: SettingsClient = LocationServices.getSettingsClient(AppInstance.get())
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

            task.addOnSuccessListener { response ->
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null
                )
            }

            task.addOnFailureListener { exception ->
                if (exception is ResolvableApiException)
                {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try
                    {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        exception.startResolutionForResult(
                            activity.get(),
                            REQUEST_CHECK_SETTINGS
                        )
                    }
                    catch (sendEx: IntentSender.SendIntentException)
                    {
                        // Ignore the error.
                    }
                }
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.let { callback(location) }
                    }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stop()
    {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}