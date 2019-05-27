package fr.jonathangerbaud.core

import android.app.Application
import java.lang.ref.WeakReference


class AppInstance private constructor(){

    companion object
    {
        private lateinit var ref:WeakReference<Application>

        fun get():Application = ref.get()!!

        fun attach(app:Application)
        {
            ref = WeakReference(app)
        }
    }



}