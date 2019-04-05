package fr.jonathangerbaud.network.rest

import com.novoda.merlin.MerlinsBeard
import fr.jonathangerbaud.core.BaseApp
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

import java.io.IOException
import java.util.concurrent.TimeUnit

import fr.jonathangerbaud.core.ext.d

class CacheControlInterceptor internal constructor(
    private val cacheDuration: Int,
    private val useOnlineCache: Boolean,
    private val useOfflineCache: Boolean
) : Interceptor
{


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response?
    {
        d("intercept")
        var request = chain.request()

        // Add Cache Control only for GET methods
        if (chain.request().method() == "GET")
        {
            if (isConnectionAvailable)
            {
                val cacheControl = CacheControl.Builder()

                if (!useOnlineCache || cacheDuration == 0)
                {
                    cacheControl.noCache()
                }
                else
                {
                    // "webservices" don't support caching headers so maxAge will always be 0
                    // so use maxStale instead of maxAge to cache responses
                    cacheControl.maxStale(cacheDuration, TimeUnit.SECONDS)
                }

                request = request.newBuilder()
                    .cacheControl(cacheControl.build())
                    .build()
            }
            else
            {
                if (useOfflineCache)
                {
                    val cacheControl = CacheControl.Builder()
                        .maxStale(cacheDuration, TimeUnit.SECONDS)
//                        .maxAge(cacheDuration, TimeUnit.SECONDS)
                        .onlyIfCached()
                        .build()

                    request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build()

                    val url = request.url().toString()

                    var response: Response? = null
                    try
                    {
                        response = chain.proceed(request)
                    }
                    catch (e: Exception)
                    {
                        d("request error" + e.message)
                        //throw e;
                    }

                    // Don't cache server errors
                    if (response != null && !response.isSuccessful)
                    {
                        ServiceBuilder.removeFromCache(url)
                    }

                    return response
                }
            }
        }

        return chain.proceed(request)
    }

    companion object
    {
        internal val isConnectionAvailable: Boolean
            get() = MerlinsBeard.from(BaseApp.instance).isConnected
    }
}
