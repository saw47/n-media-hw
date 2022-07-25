package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface PostRepository {
    val data: LiveData<List<Post>>
    fun like(id: Long)
    fun repost(id: Long)
    fun delete(id: Long)
    fun insert(content: String)
   // fun add(post: Post)

}