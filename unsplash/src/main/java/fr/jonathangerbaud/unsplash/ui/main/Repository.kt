package fr.jonathangerbaud.unsplash.ui.main

import fr.jonathangerbaud.network.networkCall
import fr.jonathangerbaud.unsplash.ui.main.model.Photo
import fr.jonathangerbaud.unsplash.ui.main.rest.ServiceBuilder
import fr.jonathangerbaud.unsplash.ui.main.rest.UnsplashApi


class Repository
{
    fun getCuratedPhotos(page:Int = 1) = networkCall<UnsplashApi.PhotoResponse, List<Photo>> {
        client = ServiceBuilder()
            .defaultCache()
            .cacheDuration(fr.jonathangerbaud.network.rest.ServiceBuilder.DAY)
            //.enableLogs()
            .build(UnsplashApi::class.java).getCuratedPhotos(page)
    }
}