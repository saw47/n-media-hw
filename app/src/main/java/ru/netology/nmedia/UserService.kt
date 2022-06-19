package ru.netology.nmedia

object UserService {
    var userIdCounter: Long = 0L
    fun setUserId(): Long {
        userIdCounter++
        return userIdCounter
    }

    fun printId(users: List<User>) {
        for (user in users) {
            print("|_${user.userId}_|")
        }
    }
}