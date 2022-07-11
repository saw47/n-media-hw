package ru.netology.nmedia.data

import ru.netology.nmedia.util.Service

data class Post(
    val postId: Long = 0,
    val authorName: String,
    val content: String,
    val link: String? = null,
    val initFavoriteList: Boolean = Service.fillPostFavoriteList(postId),
    var favoriteCounter: Int = 0,
    var repostCounter: Int = 0,
    var viewCounter: Int = 0
    )


