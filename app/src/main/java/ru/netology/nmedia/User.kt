package ru.netology.nmedia

class User(
    val userName: String
) {
    val userId: Long = UserService.setUserId()
}

object UserService {
    var userIdCounter: Long = 0
    fun setUserId(): Long {
        userIdCounter++
        return userIdCounter
    }
}