package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface PostRepository {
    val data: LiveData<List<Post>>
    fun like(id: Long)
    fun repost(id: Long)
    fun delete(id: Long)
    fun save(post: Post)

    companion object {
        const val NEW_POST_ID = -1L
    }

}