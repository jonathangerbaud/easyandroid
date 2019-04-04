package fr.jonathangerbaud.unsplash.ui.main.model

import com.google.gson.annotations.SerializedName


data class User(
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val username: String,
    val name:String,
    @SerializedName("first_name")
    val firstName:String,
    @SerializedName("last_name")
    val lastName:String,
    @SerializedName("twitter_username")
    val twitterUsername:String,
    @SerializedName("portfolio_url")
    val portfolioUrl:String,
    val bio:String,
    val location:String,
    val links:Links,
    @SerializedName("profile_image")
    val profileImage:ProfileImage,
    @SerializedName("instagram_username")
    val instragramUsername:String,
    @SerializedName("total_collections")
    val totalCollections:Int,
    @SerializedName("total_likes")
    val totalLikes:Int,
    @SerializedName("total_photos")
    val totalPhotos:Int,
    @SerializedName("accepted_tos")
    val acceptedTos:Boolean
)
{
    data class ProfileImage(
        val small:String,
        val medium:String,
        val large:String
    )

    data class Links(
        val self:String,
        val html:String,
        val photos:String,
        val likes:String,
        val portfolio:String,
        val following:String,
        val followers:String
    )
}