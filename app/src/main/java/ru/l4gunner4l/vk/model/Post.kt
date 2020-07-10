package ru.l4gunner4l.vk.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import ru.l4gunner4l.vk.utils.TimeUnits
import java.text.SimpleDateFormat
import java.util.*

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
    var isUserLike: Boolean,
    @SerializedName("likes_count")
    var likesCount: Int,
    @SerializedName("comments_count")
    val commentsCount: Int,
    @SerializedName("shares_count")
    val sharesCount: Int
): Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    fun getFormattedDate(): String {
        val SECOND = 1000L
        val MINUTE = 60 * SECOND
        val HOUR = 60 * MINUTE
        val DAY = 24 * HOUR
        val interval = Date().time - postDate
        return when {
            interval < SECOND ->
                "только что"
            interval < 45 * SECOND && interval >= SECOND ->
                "несколько секунд назад"
            interval < 75 * SECOND && interval >= 45 * SECOND ->
                "минуту"
            interval < 45 * MINUTE && interval >= 75 * SECOND ->
                // N-минут назад
                TimeUnits.MINUTE.plural((interval/ MINUTE).toInt())
            interval < 75 * MINUTE && interval >= 45 * MINUTE ->
                "час"
            interval < 22 * HOUR && interval >= 75 * MINUTE ->
                // N-часов назад
                TimeUnits.HOUR.plural((interval/ HOUR).toInt())
            interval < 24 * HOUR && interval >= 22 * HOUR ->
                "день"
            interval < 7 * DAY && interval >= 24 * HOUR ->
                // N-дней назад
                TimeUnits.DAY.plural((interval/ DAY).toInt())
            else ->
                SimpleDateFormat("dd.MM.yyyy HH:mm").format(postDate)
        }

    }

    /*fun cutPostText(): String? {
        return when {
            postText == null -> null
            postText.length > 50 -> postText.substring(0, 300)+"..."
            else -> postText
        }
    }*/

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(avatarUrl)
        parcel.writeString(userName)
        parcel.writeLong(postDate)
        parcel.writeString(postText)
        parcel.writeString(postImage)
        parcel.writeByte(if (isUserLike) 1 else 0)
        parcel.writeInt(likesCount)
        parcel.writeInt(commentsCount)
        parcel.writeInt(sharesCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }


}
