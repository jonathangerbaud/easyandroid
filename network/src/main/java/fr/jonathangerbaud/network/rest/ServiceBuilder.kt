package fr.jonathangerbaud.network.rest

import fr.jonathangerbaud.core.AppInstance
import fr.jonathangerbaud.core.ext.e
import fr.jonathangerbaud.network.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

open class ServiceBuilder(private val server: String)
{

    private var cacheDuration: Int = 0
    private var cacheSize = DEFAULT_CACHE_SIZE.toLong()
    private var useCacheIfNoConnection: Boolean = false
    private var useCacheIfConnection = true
    private var forceCache = false

    private var enableLogging = false
    private var loggingLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY

    private var reportResult: Boolean = false

    private var connectTimeout = 60
    private var readTimeout = 60
    private var writeTimeout = 60


    /**
     * Define the max valid age before cache is expired
     *
     * @param duration in seconds. Use `SECONDS`, `MINUTES`, `HOURS` and
     * `DAYS` constants to
     * help
     * @return The ServiceBuilder instance for method chaining
     */
    fun cacheDuration(duration: Int): ServiceBuilder
    {
        cacheDuration = duration
        return this
    }

    /**
     * Define the max cache size
     *
     * @param size in byte. Use `KB` and `MB` constants to help
     * @return The ServiceBuilder instance for method chaining
     */
    fun cacheSize(size: Long): ServiceBuilder
    {
        cacheSize = size
        return this
    }

    /**
     * Set the cache duration to a default 10 minutes
     * and enable cache use when no connection available
     *
     * @return The ServiceBuilder instance for method chaining
     */
    fun defaultCache(): ServiceBuilder
    {
        cacheDuration = DEFAULT_CACHE_DURATION
        cacheSize = DEFAULT_CACHE_SIZE.toLong()
        useCacheIfNoConnection = true
        return this
    }

    /**
     * Ignore server cache headers and force caching the request's response in the app
     */
    fun forceCache(): ServiceBuilder
    {
        forceCache = true
        return this
    }

    fun enableLogs(): ServiceBuilder
    {
        enableLogging = true
        return this
    }

    fun enableLogs(loggingLevel: HttpLoggingInterceptor.Level): ServiceBuilder
    {
        enableLogging = true
        this.loggingLevel = loggingLevel
        return this
    }

    fun reportResult(): ServiceBuilder
    {
        reportResult = true
        return this
    }

    /**
     * Enable cache use when no connection available
     *
     * @return The ServiceBuilder instance for method chaining
     */
    fun useCacheIfNoConnection(): ServiceBuilder
    {
        useCacheIfNoConnection = true
        return this
    }

    fun noCacheIfConnection(): ServiceBuilder
    {
        useCacheIfConnection = false
        return this
    }

    fun setConnectTimeout(connectTimeout: Int): ServiceBuilder
    {
        this.connectTimeout = connectTimeout
        return this
    }

    fun setReadTimeout(readTimeout: Int): ServiceBuilder
    {
        this.readTimeout = readTimeout
        return this
    }

    fun setWriteTimeout(writeTimeout: Int): ServiceBuilder
    {
        this.writeTimeout = writeTimeout
        return this
    }

    fun <T> build(service: Class<T>): T
    {
        return buildRetrofit().create(service)
    }

    private fun buildRetrofit(): Retrofit
    {
        val retrofitBuilder = Retrofit.Builder()
            .client(buildOkHttpClient())
            .baseUrl(server)
            .addCallAdapterFactory(CoroutineCallAdapterFactory.create())

        configureRetrofit(retrofitBuilder)

        return retrofitBuilder.build()
    }

    /**
     * Override this method to add additional options to Retrofit
     * Things like call adapters, converters, callback executors, ...
     *
     * @param retrofitBuilder
     */
    protected open fun configureRetrofit(retrofitBuilder: Retrofit.Builder)
    {

    }

    private fun buildOkHttpClient(): OkHttpClient
    {
        val okHttpBuilder = OkHttpClient.Builder()

        okHttpBuilder.connectTimeout(connectTimeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
            .writeTimeout(writeTimeout.toLong(), TimeUnit.SECONDS)

        if (useCacheIfConnection || useCacheIfNoConnection)
            okHttpBuilder.cache(buildCache(cacheSize))

        okHttpBuilder.addInterceptor { chain ->
            val request = alterRequest(chain.request())

            val builder = request.newBuilder()

            addHeaders(builder)
            chain.proceed(builder.build())
        }

        if (useCacheIfNoConnection || useCacheIfConnection)
            okHttpBuilder.addInterceptor(
                CacheControlInterceptor(
                    cacheDuration, useCacheIfConnection,
                    useCacheIfNoConnection
                )
            )

        if ((useCacheIfConnection || useCacheIfNoConnection) && forceCache)
            okHttpBuilder.addNetworkInterceptor(ForceCacheControlInterceptor(cacheDuration))

        if (enableLogging)
            okHttpBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(loggingLevel))

        if (reportResult)
            okHttpBuilder.addInterceptor(ErrorLoggerInterceptor())

        configureOkHttpClient(okHttpBuilder)

        return okHttpBuilder.build()
    }

    /**
     * Override this method to add additional options to OkHttpClient
     * Set things like interceptors, certificates, authenticator, caching, ...
     *
     * @param okHttpBuilder
     */
    protected open fun configureOkHttpClient(okHttpBuilder: OkHttpClient.Builder)
    {

    }

    /**
     * Allow to modify the original request, to add automatic uri parameters for example
     *
     * @param request
     * @return
     */
    protected open fun alterRequest(request: Request): Request
    {
        return request
    }

    /**
     * Override this method to add headers to your request
     *
     * @param builder
     */
    protected open fun addHeaders(builder: Request.Builder)
    {
        /**
         * Example
         *
         * builder.addHeader("Accept-Language", "fr");
         *
         * if (accessToken != null)
         * builder.addHeader("Authorization", tokenType + " " + accessToken);
         */
    }

    companion object
    {
        const val SECOND = 1
        const val MINUTE = 60
        const val HOUR = 60 * MINUTE
        const val DAY = 24 * HOUR

        const val KB = 1024
        const val MB = 1024 * KB

        private val DEFAULT_CACHE_DURATION = 10 * MINUTE
        private val DEFAULT_CACHE_SIZE = 10 * MB

        private var cache: Cache? = null

        /*public static <T> T parseError(ResponseBody errorBody, T clazz)
    {
        Retrofit retrofit = retrofitBuilder.build();

        Converter<ResponseBody, T> converter = retrofit.responseBodyConverter(clazz.getClass(), clazz.getClass()
                .getAnnotations());

        try
        {
            return converter.convert(errorBody);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return clazz;
    }*/

        private fun buildCache(cacheSize: Long): Cache?
        {
            if (cache == null)
            {
                try
                {
                    cache = Cache(File(AppInstance.get().cacheDir, "http-cache"), cacheSize)  // 10 MB
                }
                catch (exception: Exception)
                {
                    e(exception, "Could not create Cache!")
                }

            }

            return cache
        }

        fun removeFromCache(url: String)
        {
            if (cache != null)
            {
                try
                {
                    val it = cache!!.urls()
                    while (it.hasNext())
                    {
                        val u = it.next()
                        if (u == url)
                        {
                            it.remove()
                            break
                        }

                    }
                }
                catch (e: IOException)
                {
                    e.printStackTrace()
                }

            }
        }

        fun clearCache()
        {
            try
            {
                val dir = File(AppInstance.get().cacheDir, "http-cache")

                for (file in dir.listFiles())
                {
                    file.delete()
                }

                dir.delete()
            }
            catch (e: Exception)
            {

            }

        }
    }
}