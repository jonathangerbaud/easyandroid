package fr.jonathangerbaud.unsplash.ui.main.rest

import fr.jonathangerbaud.network.rest.ServiceBuilder
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ServiceBuilder:  ServiceBuilder("https://api.unsplash.com")
{
    override fun addHeaders(builder: Request.Builder)
    {
        builder.addHeader("Authorization", "Client-ID b8c7737978cd01262b01168cf34b8c8efbd069bee08be34e454ae001d915b82d")
    }

    override fun configureRetrofit(retrofitBuilder: Retrofit.Builder)
    {
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create())
    }
}