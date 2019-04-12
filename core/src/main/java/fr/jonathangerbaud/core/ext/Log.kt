package fr.jonathangerbaud.core.ext

import org.jetbrains.annotations.NonNls
import timber.log.Timber

//
// Static methods on the Timber object
//

/*object Timber {
    /** Log a verbose exception and a message that will be evaluated lazily when the message is printed */
    @JvmStatic inline fun v(t: Throwable? = null, message: () -> String) = log { Timber.v(t, message()) }
    @JvmStatic inline fun v(t: Throwable?) = Timber.v(t)

    /** Log a debug exception and a message that will be evaluated lazily when the message is printed */
    @JvmStatic inline fun d(t: Throwable? = null, message: () -> String) = log { Timber.d(t, message()) }
    @JvmStatic inline fun d(t: Throwable?) = Timber.d(t)
    @JvmStatic inline fun d(message: String, vararg args: Any) = Timber.d(message, args)
    @JvmStatic inline fun d(t: Throwable, @NonNls message: String, vararg args: Any) = Timber.d(t, message, args)

    /** Log an info exception and a message that will be evaluated lazily when the message is printed */
    @JvmStatic inline fun i(t: Throwable? = null, message: () -> String) = log { Timber.i(t, message()) }
    @JvmStatic inline fun i(t: Throwable?) = Timber.i(t)

    /** Log a warning exception and a message that will be evaluated lazily when the message is printed */
    @JvmStatic inline fun w(t: Throwable? = null, message: () -> String) = log { Timber.w(t, message()) }
    @JvmStatic inline fun w(t: Throwable?) = Timber.w(t)

    /** Log an error exception and a message that will be evaluated lazily when the message is printed */
    @JvmStatic inline fun e(t: Throwable? = null, message: () -> String) = log { Timber.e(t, message()) }
    @JvmStatic inline fun e(t: Throwable?) = Timber.e(t)
    @JvmStatic inline fun e(message: String, vararg args: Any) = Timber.e(message, args)
    @JvmStatic inline fun e(t: Throwable, @NonNls message: String, vararg args: Any) = Timber.e(t, message, args)

    /** Log an assert exception and a message that will be evaluated lazily when the message is printed */
    @JvmStatic inline fun wtf(t: Throwable? = null, message: () -> String) = log { Timber.wtf(t, message()) }
    @JvmStatic inline fun wtf(t: Throwable?) = Timber.wtf(t)
}*/

//
// Plain functions
//

/** Log a verbose exception and a message that will be evaluated lazily when the message is printed */
inline fun v(t: Throwable? = null, message: () -> String) = log { Timber.v(t, message()) }
fun v(t: Throwable?) = Timber.v(t)

/** Log a debug exception and a message that will be evaluated lazily when the message is printed */
inline fun d(t: Throwable? = null, message: () -> String) = log { Timber.d(t, message()) }
fun d(t: Throwable?) = Timber.d(t)
fun d(message: String) = Timber.d(message)
fun d(message: String, vararg args: Any) = Timber.d(message, args)
fun d(t: Throwable, @NonNls message: String, vararg args: Any) = Timber.d(t, message, args)


/** Log an info exception and a message that will be evaluated lazily when the message is printed */
inline fun i(t: Throwable? = null, message: () -> String) = log { Timber.i(t, message()) }
fun i(t: Throwable?) = Timber.i(t)

/** Log a warning exception and a message that will be evaluated lazily when the message is printed */
inline fun w(t: Throwable? = null, message: () -> String) = log { Timber.w(t, message()) }
fun w(t: Throwable? = null, message: String) = log { Timber.w(t, message) }
fun w(t: Throwable?) = Timber.w(t)

/** Log an error exception and a message that will be evaluated lazily when the message is printed */
inline fun e(t: Throwable? = null, message: () -> String) = log { Timber.e(t, message()) }
fun e(t: Throwable?) = Timber.e(t)
fun e(message: String, vararg args: Any) = Timber.e(message, args)
fun e(t: Throwable, @NonNls message: String, vararg args: Any) = Timber.e(t, message, args)

/** Log an assert exception and a message that will be evaluated lazily when the message is printed */
inline fun wtf(t: Throwable? = null, message: () -> String) = log { Timber.wtf(t, message()) }
fun wtf(t: Throwable?) = Timber.wtf(t)

/** @suppress */
@PublishedApi
internal inline fun log(block: () -> Unit) {
    if (Timber.treeCount() > 0) block()
}