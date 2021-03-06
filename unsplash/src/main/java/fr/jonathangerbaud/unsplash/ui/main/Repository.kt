package fr.jonathangerbaud.unsplash.ui.main

import fr.jonathangerbaud.network.networkApiCall
import fr.jonathangerbaud.network.networkCall
import fr.jonathangerbaud.unsplash.ui.main.model.Photo
import fr.jonathangerbaud.unsplash.ui.main.rest.ServiceBuilder
import fr.jonathangerbaud.unsplash.ui.main.rest.UnsplashApi


class Repository
{
    fun getCuratedPhotos(page:Int = 1, perPage:Int = 10) = networkApiCall<List<Photo>, UnsplashApi.PhotoResponse> {
        client = ServiceBuilder()
            .noCacheIfConnection()
//            .defaultCache()
//            .cacheDuration(fr.jonathangerbaud.network.rest.ServiceBuilder.DAY)
//            .forceCache()
//            .noCacheIfConnection()
            .enableLogs()
//            .reportResult()
            .build(UnsplashApi::class.java).getCuratedPhotos(page, perPage)
    }

    fun getCuratedPhotos2(page:Int = 1) = networkCall<List<Photo>> {
        client = ServiceBuilder()
            .defaultCache()
            .cacheDuration(fr.jonathangerbaud.network.rest.ServiceBuilder.DAY)
            //.enableLogs()
            .build(UnsplashApi::class.java).getCuratedPhotos2(page)
    }
}