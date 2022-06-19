package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepo {
    val data: LiveData<List<Post>>
    fun like(post: Post)
    fun repost(post: Post)
}