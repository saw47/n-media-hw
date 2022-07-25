package ru.netology.nmedia.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.data.impl.SharedPrefsPostRepository
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {

    private val repository = FilePostRepository(application)

    val data get() = repository.data

    val shareContent = SingleLiveEvent<String>()
    val urlContent = SingleLiveEvent<String?>()
    val editPost = SingleLiveEvent<Post>()

    fun onSaveButtonClicked(newContent: String) {
        if (newContent.isBlank()) return
        repository.insert(newContent)
    }

    override fun onLikeClick(id: Long) = repository.like(id)

    override fun onRepostClick(post: Post) {
        shareContent.value = post.content
        repository.repost(post.postId)
    }

    override fun onRemoveClick(id: Long) {
        repository.delete(id)
    }

    override fun onEditClick(post: Post) {
        repository.tempPost = post
        editPost.value = post
    }

    override fun onVideoLinkClicked(post: Post) {
        urlContent.value = post.video
    }
}
