package ru.netology.nmedia

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

object PostService {

    private var postIdCounter: Long = 0
    fun setPostId(): Long = postIdCounter + 1L

//    fun Post.checkILikeItPost(user: User): Boolean {
//        return thoseWhoLikedIt.contains(user.userId)
//    }



    @SuppressLint("SimpleDateFormat")
    fun Post.getSimpleDateFormat(): String {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val date = Date().time
        return dateFormatter.format(date)
    }

    fun peopleCounter(quantity: Int): String {
        return when (quantity) {
            in 1..999 -> quantity.toString()
            in 1000..9999 -> {
                if (floor(quantity.toDouble() / 100) % 10 == 0.0) {
                    String.format("%.0f", quantity.toDouble() / 1000).plus("k")
                } else {
                    String.format("%.1f", quantity.toDouble() / 1000).plus("k")
                }
            }
            in 10000..999999 -> String.format("%.0f", floor(quantity.toDouble() / 1000)).plus("k")
            in 1000000..Int.MAX_VALUE -> {
                if (floor(quantity.toDouble() / 100000) % 10 == 0.0) {
                    String.format("%.0f", quantity.toDouble() / 1000000).plus("M")
                } else {
                    String.format("%.1f", quantity.toDouble() / 1000000).plus("M")
                }
            }
            else -> ""
        }
    }
}