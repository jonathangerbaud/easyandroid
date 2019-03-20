package fr.jonathangerbaud.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.hardware.*
import android.location.Location
import android.view.Surface
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


class MapBearingDelegate(
    activity: Activity,
    private val lifecycle: Lifecycle,
    private val callback: (Double) -> Unit
) : LifecycleObserver
{
    private var enabled = false
    private val sensorManager: SensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    private val accelerometerSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val magneticSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private var declination: Float = 0f
    private val rotationMatrix = FloatArray(16)

    private var accelerometerData = FloatArray(3)
    private var magnetometerData = FloatArray(3)

    private val windowManager: WindowManager = activity.windowManager

    private val sensorListener = object : SensorEventListener
    {
        override fun onSensorChanged(event: SensorEvent?)
        {
            when
            {
                event!!.sensor.type == Sensor.TYPE_ROTATION_VECTOR -> onRotationVectorChange(event)
                event.sensor.type == Sensor.TYPE_ACCELEROMETER     ->
                {
                    accelerometerData = event.values.clone()
                    onOldSensorChange()
                }
                event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD    ->
                {
                    magnetometerData = event.values.clone()
                    onOldSensorChange()
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int)
        {

        }
    }

    private fun onRotationVectorChange(event: SensorEvent)
    {
        SensorManager.getRotationMatrixFromVector(
            rotationMatrix, event.values
        )

        val worldAxisForDeviceAxisX: Int
        val worldAxisForDeviceAxisY: Int

        // Remap the axes as if the device screen was the instrument panel,
        // and adjust the rotation matrix for the device orientation.
        when (windowManager.defaultDisplay.rotation)
        {
            Surface.ROTATION_0   ->
            {
                worldAxisForDeviceAxisX = SensorManager.AXIS_Z
                worldAxisForDeviceAxisY = SensorManager.AXIS_Y
            }
            Surface.ROTATION_90  ->
            {
                worldAxisForDeviceAxisX = SensorManager.AXIS_Y
                worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_Z
            }
            Surface.ROTATION_180 ->
            {
                worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_Z
                worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_Y
            }
            Surface.ROTATION_270 ->
            {
                worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_Y
                worldAxisForDeviceAxisY = SensorManager.AXIS_Z
            }
            else                 ->
            {
                worldAxisForDeviceAxisX = SensorManager.AXIS_Z
                worldAxisForDeviceAxisY = SensorManager.AXIS_Y
            }
        }

        val adjustedRotationMatrix = FloatArray(16)
        SensorManager.remapCoordinateSystem(
            rotationMatrix, worldAxisForDeviceAxisX,
            worldAxisForDeviceAxisY, adjustedRotationMatrix
        )

        // Transform rotation matrix into azimuth/pitch/roll
        val orientation = FloatArray(3)
        SensorManager.getOrientation(adjustedRotationMatrix, orientation)
        val bearing = Math.toDegrees(orientation[0].toDouble())

        callback(bearing)
    }

    private fun onOldSensorChange()
    {
        val rotationMatrix = FloatArray(9)
        val rotationOK = SensorManager.getRotationMatrix(
            rotationMatrix,
            null, accelerometerData, magnetometerData
        )

        val worldAxisForDeviceAxisX: Int
        val worldAxisForDeviceAxisY: Int

        when (windowManager.defaultDisplay.rotation)
        {
            Surface.ROTATION_0   ->
            {
                worldAxisForDeviceAxisX = SensorManager.AXIS_X
                worldAxisForDeviceAxisY = SensorManager.AXIS_Y
            }
            Surface.ROTATION_90  ->
            {
                worldAxisForDeviceAxisX = SensorManager.AXIS_Y
                worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_X
            }
            Surface.ROTATION_180 ->
            {
                worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_X
                worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_Y
            }
            Surface.ROTATION_270 ->
            {
                worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_Y
                worldAxisForDeviceAxisY = SensorManager.AXIS_X
            }
            else                 ->
            {
                worldAxisForDeviceAxisX = SensorManager.AXIS_X
                worldAxisForDeviceAxisY = SensorManager.AXIS_Y
            }
        }

        val adjustedRotationMatrix = FloatArray(9)
        SensorManager.remapCoordinateSystem(
            rotationMatrix, worldAxisForDeviceAxisX,
            worldAxisForDeviceAxisY, adjustedRotationMatrix
        )

        val orientationValues = FloatArray(3)
        if (rotationOK)
        {
            SensorManager.getOrientation(adjustedRotationMatrix, orientationValues)

            val azimuth = orientationValues[0]
            val bearing = Math.toDegrees(azimuth.toDouble())
            callback(bearing)
        }
    }

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

    fun setLocation(location: Location)
    {
        val field = GeomagneticField(
            location.latitude.toFloat(),
            location.longitude.toFloat(),
            location.altitude.toFloat(),
            System.currentTimeMillis()
        )

        // getDeclination returns degrees
        declination = field.declination
    }

    fun canDetectBearing(): Boolean
    {
        return sensor != null || (accelerometerSensor != null && magneticSensor != null)
    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun start()
    {
        if (enabled)
        {
            if (sensor != null)
            {
                sensorManager.registerListener(
                    sensorListener,
                    sensor,
                    SensorManager.SENSOR_DELAY_NORMAL
                )
            }
            else if (accelerometerSensor != null && magneticSensor != null)
            {
                sensorManager.registerListener(
                    sensorListener,
                    accelerometerSensor,
                    SensorManager.SENSOR_DELAY_NORMAL
                )
                sensorManager.registerListener(
                    sensorListener,
                    magneticSensor,
                    SensorManager.SENSOR_DELAY_NORMAL
                )
            }

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stop()
    {
        sensorManager.unregisterListener(sensorListener)
    }
}