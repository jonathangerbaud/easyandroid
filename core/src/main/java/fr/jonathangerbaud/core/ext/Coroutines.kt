package fr.jonathangerbaud.core.ext

import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main


fun io(work: suspend (() -> Unit)): Job =
    CoroutineScope(Dispatchers.IO).launch {
        work()
    }

fun <T : Any> ioThenMain(work: suspend (() -> T?), callback: ((T?) -> Unit)): Job =
    CoroutineScope(Dispatchers.Main).launch {
        val data = CoroutineScope(Dispatchers.IO).async rt@{
            return@rt work()
        }.await()
        callback(data)
    }

fun <T : Any> ioThenMainDelayed(delayInMillis:Long, work: suspend (() -> T?), callback: ((T?) -> Unit)): Job =
    CoroutineScope(Dispatchers.Main).launch {
        val data = CoroutineScope(Dispatchers.IO).async rt@{
            delay(delayInMillis)
            return@rt work()
        }.await()
        callback(data)
    }