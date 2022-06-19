package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepo
import ru.netology.nmedia.data.impl.InMemoryPostRepo

class PostViewModel: ViewModel() {

    private val repository: PostRepo = InMemoryPostRepo()

    val data by repository::data

    fun onLikeClick(post: Post) {
        repository.like(post)
        println("from viewmodel postId - ${post.postId}")
    }

    fun onRepostClick(post: Post) = repository.repost(post)


}