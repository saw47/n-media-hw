package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel: ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data get() = repository.data

    fun onLikeClick(id: Long) {
        repository.like(id)
    }

    fun onRepostClick(postId: Long) = repository.repost(postId)


}