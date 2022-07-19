package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository.posts
import ru.netology.nmedia.util.Service

object InMemoryPostRepository : PostRepository {

    private const val GENERATED_POSTS_AMOUNT = 1000

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

    var tempPost:Post? = null

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

    override fun insert(content: String) {
        if (tempPost == null){
            val id = ++postCounter
            Service.fillPostFavoriteList(id)
            data.value = listOf(
                Post(
                    postId = id,
                    content = content,
                    authorName = "new author"
                )
            ) + posts
        } else {
            data.value = posts.map {
                if (it.postId == tempPost!!.postId) tempPost!!.copy(content = content) else it
            }
            tempPost = null
        }
    }
}