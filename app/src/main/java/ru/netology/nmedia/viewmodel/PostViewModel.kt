package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.PostRepo
import ru.netology.nmedia.data.impl.InMemoryPostRepo

class PostViewModel: ViewModel() {

    private val repository: PostRepo = InMemoryPostRepo()

    val data by repository::data

    fun onLikeClick() = repository.like()

    fun onRepostClick() = repository.repost()


}