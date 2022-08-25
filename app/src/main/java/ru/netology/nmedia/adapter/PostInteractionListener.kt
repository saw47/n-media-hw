package ru.netology.nmedia.adapter

import ru.netology.nmedia.data.Post

interface PostInteractionListener {
    fun onLikeClick(post: Post)
    fun onRepostClick(post: Post)
    fun onRemoveClick(id: Long)
    fun onEditClick(post: Post)
    fun onVideoLinkClicked(post: Post)
    fun onFrameClick(post: Post)
}