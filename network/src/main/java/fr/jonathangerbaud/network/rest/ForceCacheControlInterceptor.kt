package fr.jonathangerbaud.network.rest

import fr.jonathangerbaud.core.ext.d
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ForceCacheControlInterceptor internal constructor(private val cacheDuration: Int) : Interceptor
{

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response?
    {
        d("intercept")
        var response: Response? = null
        try
        {
            response = chain.proceed(chain.request())
        }
        catch (e: Exception)
        {
            d("request error" + e.message)
            //throw e;
        }

        if (response != null)
        {
            return response.newBuilder().header("cache-control", "max-stale=$cacheDuration").build()
        }

        return response
    }
}
