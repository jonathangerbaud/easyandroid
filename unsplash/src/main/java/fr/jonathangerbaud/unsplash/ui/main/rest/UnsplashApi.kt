package fr.jonathangerbaud.unsplash.ui.main.rest

import fr.jonathangerbaud.network.DataResponse
import fr.jonathangerbaud.unsplash.ui.main.model.Photo
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface UnsplashApi
{
    @GET("/photos/curated")
    fun getCuratedPhotos(@Query("page") page:Int = 1): Deferred<Response<PhotoResponse>>

    class PhotoResponse : ArrayList<Photo>(), DataResponse<List<Photo>>
    {
        override fun retrieveData(): List<Photo> = this
    }
}