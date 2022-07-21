package ru.netology.nmedia.viewmodel

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel : ViewModel(), PostInteractionListener {

    val data get() = InMemoryPostRepository.data
    val shareContent = SingleLiveEvent<String>()
    val urlContent = SingleLiveEvent<String?>()
    val editPost = SingleLiveEvent<Post>()

    fun onSaveButtonClicked(newContent: String) {
        if (newContent.isBlank()) return
        InMemoryPostRepository.insert(newContent)
    }

    override fun onLikeClick(id: Long) = InMemoryPostRepository.like(id)

    override fun onRepostClick(post: Post) {
        shareContent.value = post.content
        InMemoryPostRepository.repost(post.postId)
    }

    override fun onRemoveClick(id: Long) {
        InMemoryPostRepository.delete(id)
    }

    override fun onEditClick(post: Post) {
        InMemoryPostRepository.tempPost = post
        editPost.value = post
    }

    override fun onVideoLinkClicked(post: Post) {
        urlContent.value = post.video
    }
}
