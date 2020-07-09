package ru.l4gunner4l.vk.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id")
    val id: Long,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("username")
    val userName: String,
    @SerializedName("post_date")
    val postDate: Long,
    @SerializedName("post_text")
    val postText: String?,
    @SerializedName("post_image")
    val postImage: String?,
    @SerializedName("is_user_like")
    val isUserLike: Boolean,
    @SerializedName("likes_count")
    val likesCount: Int,
    @SerializedName("comments_count")
    val commentsCount: Int,
    @SerializedName("shares_count")
    val sharesCount: Int
){

}
