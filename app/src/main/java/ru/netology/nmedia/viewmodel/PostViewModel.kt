package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data get() = repository.data

    val currentPost = MutableLiveData<Post?>(null)


    fun onSaveButtonClicked(content: String) {
        if(content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            postId = PostRepository.NEW_POST_ID,
            authorName = "New Post Author",
            content = content
        )

        repository.save(post)
        currentPost.value = null
    }

    override fun onLikeClick(id: Long) = repository.like(id)

    override fun onRepostClick(id: Long) = repository.repost(id)

    override fun onRemoveClick(id: Long) = repository.delete(id)

    override fun onEditClick(post: Post) {
        currentPost.value = post
    }
}