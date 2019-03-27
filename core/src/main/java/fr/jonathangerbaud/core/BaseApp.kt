package fr.jonathangerbaud.core

import android.app.Application
import timber.log.Timber

open class BaseApp : Application()
{

    override fun onCreate()
    {
        super.onCreate()

        instance = this

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    companion object
    {
        lateinit var instance: BaseApp
            private set
    }
}
