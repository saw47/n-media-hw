package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository


class InMemoryPostRepository : PostRepository {

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val initPosts = List(100) { index ->
            Post(
                postId = index.toLong(),
                authorName = "author $index",
                content = "post content number $index \n",
                link = "$index.com",
                iLikeIt = false
            )
        }
        data = MutableLiveData(initPosts)
    }


    override fun like(id: Long) {
        posts = posts.map { post ->
            if (post.postId == id) post.copy(iLikeIt = !post.iLikeIt)
            else post
        }
    }

    override fun repost(id: Long) {
        TODO("Not yet implemented")
    }


}