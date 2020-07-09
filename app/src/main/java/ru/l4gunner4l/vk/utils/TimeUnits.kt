package ru.l4gunner4l.vk.utils

enum class TimeUnits {
    SECOND, MINUTE, HOUR, DAY;
    fun plural(value: Int):String{
        return when (this) {

            SECOND -> "$value ${when {
                value < 2 || value % 10 == 1 -> "секунду"
                value in 2..4 || value % 10 in 2..4 -> "секунды"
                else -> "секунд"}}"

            MINUTE -> "$value ${when {
                value < 2 || value % 10 == 1 -> "минуту"
                value in 2..4 || value % 10 in 2..4 -> "минуты"
                else -> "минут"}}"

            HOUR -> "$value ${when {
                value < 2 || value % 10 == 1 -> "час"
                value in 2..4 || value % 10 in 2..4 -> "часа"
                else -> "часов"}}"

            DAY -> "$value ${when {
                value < 2 || value % 10 == 1 -> "день"
                value in 2..4 || value % 10 in 2..4 -> "дня"
                else -> "дней"}}"
        }
    }
}