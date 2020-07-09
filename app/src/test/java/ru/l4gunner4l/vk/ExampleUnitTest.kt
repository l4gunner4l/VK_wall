package ru.l4gunner4l.vk

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun getTime() {
        val weekInMillis: Long = 7*24*60*60*1000
        val dayInMillis: Long = 24*60*60*1000

        val nowInMillis: Long = Date().time
        println(SimpleDateFormat("dd.MM.yyyy HH:mm").format(nowInMillis))

        val postDate = nowInMillis
        println(SimpleDateFormat("dd.MM.yyyy HH:mm").format(postDate))

        val result = if (nowInMillis - postDate > weekInMillis) {
            SimpleDateFormat("dd.MM.yyyy HH:mm").format(postDate)
        } else {
            val days: Int = ((nowInMillis - postDate)/(1000*60*60*24)).toInt()
            "$days дней назад"
        }
        println(result)
    }
}
