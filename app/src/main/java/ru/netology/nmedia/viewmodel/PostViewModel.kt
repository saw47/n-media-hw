package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel: ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data get() = repository.data

    fun onLikeClick(id: Long) {
        repository.like(id)
    }

    fun onRepostClick(post: Post) = repository.repost(post.postId)


}