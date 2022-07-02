package ru.netology.nmedia.data

import ru.netology.nmedia.data.service.Service

data class User(
    val userId: Long,
    val userName: String,
    //val addToLikeRepository: Boolean = Service.addNewUser(userId)
)