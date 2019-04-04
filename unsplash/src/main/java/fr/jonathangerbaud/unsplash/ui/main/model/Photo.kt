package fr.jonathangerbaud.unsplash.ui.main.model

import com.google.gson.annotations.SerializedName


data class Photo(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val width: Int,
    val height: Int,
    val color: String,
    val description: String?,
    @SerializedName("alt_description")
    val altDescription: String?,
    val urls:Urls,
    val links:Links,
    val categories:List<String>,
    val sponsored:Boolean,
    @SerializedName("sponsored_by")
    val sponsoredBy:String?,
    @SerializedName("sponsored_impressions_id")
    val sponsoredImpressionsId:String?,
    val likes: Int,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    @SerializedName("current_user_collection")
    val currentUserCollection:List<UnsplashCollection>,
    val user:User
)

data class Urls(
    val raw:String,
    val full:String,
    val regular:String,
    val small:String,
    val thumb:String
)

data class Links(
    val self:String,
    val html:String,
    val download:String,
    @SerializedName("download_location")
    val downloadLocation:String
)

data class UnsplashCollection(
    val id:String,
    val title:String,
    val publishedAt:String,
    val updatedAt:String,
    val coverPhoto:String?,
    val user:String?
)