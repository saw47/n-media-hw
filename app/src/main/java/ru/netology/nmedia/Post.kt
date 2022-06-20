package ru.netology.nmedia

import ru.netology.nmedia.PostService.setPostId

data class Post(
    val postId: Long =0,
    val authorName: String,
    val content: String,
    var link: String? = null,
    var iLikeIt: Boolean = false
)



