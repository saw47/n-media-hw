package ru.netology.nmedia.adapter

import ru.netology.nmedia.data.Post

interface PostInteractionListener {
    fun onLikeClick(id: Long)
    fun onRepostClick(id: Long)
    fun onRemoveClick(id: Long)
    fun onEditClick(post: Post)
    fun onCancelClick()
}