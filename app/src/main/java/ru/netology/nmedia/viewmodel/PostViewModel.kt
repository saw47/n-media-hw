package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {

    private val repository = FilePostRepository(application)
    val data get() = repository.data

    val shareContent = SingleLiveEvent<String>()
    val urlContent = SingleLiveEvent<String?>()
    val editPost = SingleLiveEvent<Post>()
    val expandPost = SingleLiveEvent<Post>()
    val fullSizePost = MutableLiveData<Post>()

    fun onSaveButtonClicked(newContent: String?, newVideoLink: String?) {
        if (newContent.isNullOrBlank() && newVideoLink.isNullOrBlank()) return
        val id = repository.tempPost?.postId
        repository.insert(newContent, newVideoLink)
        fullSizePost.value = repository.data.value?.find { it.postId == id }
    }

    override fun onLikeClick(post: Post) {
        repository.like(post.postId)
        fullSizePost.value = repository.data.value?.find { it.postId == post.postId }
    }

    override fun onRepostClick(post: Post) {
        shareContent.value = post.content ?: "А передачу видео мы пока не поддерживаем :("
        repository.repost(post.postId)
        fullSizePost.value = repository.data.value?.find { it.postId == post.postId }
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

    override fun onFrameClick(post: Post) {
        expandPost.value = post
        fullSizePost.value = post
    }
}
