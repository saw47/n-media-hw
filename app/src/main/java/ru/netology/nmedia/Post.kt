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
    val content: String,
    var link: String? = null
) {
    var counter = 0
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



