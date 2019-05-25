package fr.jonathangerbaud.core

import android.app.Application
import timber.log.Timber

open class BaseApp : Application()
{

    override fun onCreate()
    {
        super.onCreate()

        AppInstance.attach(this)

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    companion object
    {
        val instance: Application
            get() = AppInstance.get()
    }
}
