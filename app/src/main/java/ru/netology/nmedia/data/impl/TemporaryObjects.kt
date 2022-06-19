package ru.netology.nmedia.data.impl

import ru.netology.nmedia.Post
import ru.netology.nmedia.PostService
import ru.netology.nmedia.User
import kotlin.random.Random

object TemporaryObjects {

    val users = generateNewUsers(10)
    val posts = generateNewRandomPost(20)

    private fun generateNewUsers(q: Int): List<User> {
        val users = mutableListOf<User>()
        var counter: Int = -1
        while (counter++ != q) {
            users.add(User(userName = "User number $counter"))
        }
        return users
    }


    fun generateNewRandomPost(q: Int): List<Post> {
        var posts = mutableListOf<Post>()
        var counter: Int = -1
        while (counter++ != q) {
            posts.add(
                Post(
                    authorName = "author $counter",
                    content = "$counter \n" +
                            "Сквозь ветхую крышу текла озорная заря\n" +
                            "текла безмятежно и густо\n" +
                            "Сквозь ветхую крышу на запятнанные простыни\n",
                    link = "$counter.com"
                )
            )
        }
        return posts
    }
}