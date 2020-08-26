package ru.l4gunner4l.vk.task1

data class User(
    val email: String,
    val name: String,
    val lastName: String,
    val avatarLink: String,
    val status: String,
    var isOnline: Boolean
) {
    fun getFullName() = "$name $lastName"
}
