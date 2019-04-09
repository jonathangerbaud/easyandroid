package fr.jonathangerbaud.unsplash.ui.main.rest

import fr.jonathangerbaud.network.ApiResponse
import fr.jonathangerbaud.unsplash.ui.main.model.Photo
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface UnsplashApi
{
    @GET("/photos/curated")
    fun getCuratedPhotos(@Query("page") page:Int = 1, @Query("per_page") perPage:Int = 10): Deferred<Response<PhotoResponse>>

    @GET("/photos/curated")
    fun getCuratedPhotos2(@Query("page") page:Int = 1): Deferred<Response<List<Photo>>>

    interface UnsplashApiResponse<T> : ApiResponse<T>
    {
        override fun isError(): Boolean
        {
            return false
        }

        override fun getErrorMsg(): String?
        {
            return "Unsplash error"
        }

        override fun getErrorCode(): Int?
        {
            return 0
        }

        override fun getErrorExtra(): Any?
        {
            return null
        }
    }

    class PhotoResponse : ArrayList<Photo>(), UnsplashApiResponse<List<Photo>>
    {
        override fun getData(): List<Photo>
        {
            return this
        }
    }
}