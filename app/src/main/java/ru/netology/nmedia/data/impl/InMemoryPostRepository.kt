package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.util.Service

class InMemoryPostRepository : PostRepository {

    private var postCounter = GENERATED_POSTS_AMOUNT.toLong()

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val initPosts = List(GENERATED_POSTS_AMOUNT) { index ->
            Post(
                postId = index.toLong(),
                authorName = "author number $index",
                content = "post content number $index \n",
                link = "website$index.com",
            )
        }
        data = MutableLiveData(initPosts)
    }

    override fun like(id: Long) {
        posts = posts.map { post ->
            if (post.postId == id) {
                Service.addPostToFavoritesList(id)
                post.copy(favoriteCounter = Service.likeCounter(id))
            } else post
        }
    }

    override fun repost(id: Long) {
        posts = posts.map { post ->
            if (post.postId == id) {
                Service.repost(id)
                post.copy(repostCounter = Service.repostCount(id))
            } else {
                post
            }
        }
    }

    override fun delete(id: Long) {
        data.value = posts.filterNot { id == it.postId }
    }

    override fun save(post: Post) {
        if (post.postId == PostRepository.NEW_POST_ID) insert(post) else update(post)

    }

    private fun insert(post: Post) {
        val id = ++postCounter
        Service.fillPostFavoriteList(id)
        data.value = listOf(
            post.copy(
                postId = id,
                content = post.content,
                authorName = post.authorName
            )
        ) + posts


        println("SAVE")
        println(id)
        println(post.postId)
        println(post.content)
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if(it.postId == post.postId) post else it
        }
        println("UPD")
        println(post.postId)
        println(post.content)
    }

    companion object {
        const val GENERATED_POSTS_AMOUNT = 1000
    }


}