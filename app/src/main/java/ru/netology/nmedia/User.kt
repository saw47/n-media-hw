package ru.netology.nmedia

class User(
    val userName: String
) {
    val userId: Long = UserService.setUserId()
    var postByLikeMe = mutableSetOf<Long>()
    var postByRepostMe = mutableSetOf<Long>()
}
