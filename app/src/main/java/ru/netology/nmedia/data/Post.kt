package ru.netology.nmedia.data

import kotlinx.serialization.SerialInfo
import kotlinx.serialization.Serializable
import ru.netology.nmedia.util.Service

@Serializable
data class Post(
    val postId: Long = 0,
    val authorName: String,
    val content: String,
    val link: String? = null,
    val initFavoriteList: Boolean = Service.fillPostFavoriteList(postId),
    var favoriteCounter: Int = 0,
    var repostCounter: Int = 0,
    var viewCounter: Int = 0,
    var video: String? = null
    )


