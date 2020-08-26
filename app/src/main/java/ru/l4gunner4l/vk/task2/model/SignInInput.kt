package ru.l4gunner4l.vk.task2.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Patterns

data class SignInInput(
    var email: String,
    var password: String
) : Parcelable {

    fun isValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                password.length >= 8
    }


    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SignInInput> {
        override fun createFromParcel(parcel: Parcel): SignInInput {
            return SignInInput(parcel)
        }

        override fun newArray(size: Int): Array<SignInInput?> {
            return arrayOfNulls(size)
        }
    }

}