package ru.netology.nmedia

object UserService {
    var userIdCounter: Long = 0
    fun setUserId(): Long {
        userIdCounter++
        return userIdCounter
    }
}