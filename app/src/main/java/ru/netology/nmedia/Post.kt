package ru.netology.nmedia

import ru.netology.nmedia.PostService.setPostId

class Post(
    val authorName: String,
    val content: String,
    var link: String? = null
) {
    val postId: Long = setPostId()
    val postDate = System.currentTimeMillis()

    var thoseWhoShareIt = mutableListOf<Long>()
    var shareCounter: Int = thoseWhoShareIt.size

    var thoseWhoLikedIt = mutableSetOf<Long>()
    var likeCounter: Int = thoseWhoLikedIt.size
}



