package ru.netology.nmedia

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.PostService.likePost
import ru.netology.nmedia.PostService.setPostId
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.*

class Post(
    val authorName: String,
    val content: String
) {
    val postId: Long = setPostId()
    val postDate = System.currentTimeMillis()

    var thoseWhoShareIt = mutableListOf<Long>()
    var shareCounter: Int = thoseWhoShareIt.size
    var stringShareCounter: String =
        if (thoseWhoShareIt.size == 0) "" else thoseWhoShareIt.size.toString()

    var thoseWhoLikedIt = mutableSetOf<Long>()
    var likeCounter: Int = thoseWhoLikedIt.size
    var stringLikeCounter: String =
        if (thoseWhoLikedIt.size == 0) "" else thoseWhoLikedIt.size.toString()

}

object PostService {

    private var postIdCounter: Long = 0

    fun setPostId(): Long {
        return postIdCounter + 1L
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun Post.likePost(user: User) {
        if (!this.checkILikeItPost(user)) {
            this.thoseWhoLikedIt.add(user.userId)
        } else {
            this.thoseWhoLikedIt.remove(user.userId)
        }
        likeCounter = thoseWhoLikedIt.size
        //likeCounter += 1000000
    }

    fun Post.checkILikeItPost(user: User): Boolean {
        return thoseWhoLikedIt.contains(user.userId)
    }

    @SuppressLint("SimpleDateFormat")
    fun Post.getSimpleDateFormat(): String {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val date = Date(this.postDate)
        return dateFormatter.format(date)
    }

    fun Post.sharePost(user: User) {
        this.thoseWhoShareIt.add(user.userId)
        shareCounter = thoseWhoShareIt.size
        //shareCounter += 1100000

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

