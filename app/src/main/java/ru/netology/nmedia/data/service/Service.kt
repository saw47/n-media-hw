package ru.netology.nmedia.data.service

import android.annotation.SuppressLint
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

object Service {

    private val likedPostsRepo: MutableMap<Long, MutableSet<Long>> = mutableMapOf()
    private val repostCounter: MutableList<Long> = mutableListOf()

    private val user = User(
        userId = 1L,
        userName = "Random User"
    )

    fun repost(postId: Long) = repostCounter.add(postId)
    fun repostCount(postId: Long) = repostCounter.filter { it == postId }.size

    fun fillPostFavoriteList(postId: Long): Boolean {
        likedPostsRepo[postId] = mutableSetOf()
        return likedPostsRepo.containsKey(postId)
    }
    fun likeCounter(postId: Long) = likedPostsRepo[postId]!!.size

    fun addPostToFavoritesList(postId: Long) =
        if (!likedPostsRepo[postId]!!.contains(user.userId)) {
            likedPostsRepo[postId]!!.add(user.userId)
        } else {
            likedPostsRepo[postId]!!.remove(user.userId)
        }

    @SuppressLint("SimpleDateFormat")
    fun Post.getSimpleDateFormat(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date())
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
