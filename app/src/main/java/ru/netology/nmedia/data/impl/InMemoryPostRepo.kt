package ru.netology.nmedia.data.impl

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.PostService.likePost
import ru.netology.nmedia.PostService.sharePost
import ru.netology.nmedia.data.PostRepo

class InMemoryPostRepo : PostRepo {

    override val data = MutableLiveData<Post>(
        TemporaryObjects.somePost
    )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value can't be null"
        }
        currentPost.likePost(TemporaryObjects.postBrowsingUser)
        data.value = currentPost
    }

    override fun repost() {
        val sharedPost = checkNotNull(data.value) {
            "Data value can't be null"
        }
        sharedPost.sharePost(TemporaryObjects.postBrowsingUser)
        data.value = sharedPost
    }
}