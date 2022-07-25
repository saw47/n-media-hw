package ru.netology.nmedia.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.util.Service
import kotlin.properties.Delegates

class SharedPrefsPostRepository(application: Application) : PostRepository {

    companion object {
        private const val GENERATED_POSTS_AMOUNT = 1000
        private const val POST_PREFS_KEY = "posts"
        private const val NEXT_ID_PREFS_KEY = "id"
    }

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE)

    private var postCounter: Long by Delegates.observable(
        prefs.getLong(NEXT_ID_PREFS_KEY, 0L)
    ) {_, _, newValue ->
        prefs.edit {putLong(NEXT_ID_PREFS_KEY, newValue)}
    }

//    private var postCounter = GENERATED_POSTS_AMOUNT.toLong()

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            prefs.edit {
                val serializedPosts = Json.encodeToString(value)
                putString(POST_PREFS_KEY, serializedPosts)
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

//    init {
//        val initPosts = List(GENERATED_POSTS_AMOUNT) { index ->
//            Post(
//                postId = index.toLong(),
//                authorName = "author number $index",
//                content = "post content number $index \n",
//                link = "website$index.com",
//            )
//        }
//        initPosts[1].video = "https://www.youtube.com/watch?v=NghsA5B2yac"
//        initPosts[3].video = "https://www.youtube.com/watch?v=NghsA5B2yac"
//        initPosts[5].video = "https://www.youtube.com/watch?v=NghsA5B2yac"
//        data = MutableLiveData(initPosts)
//    }

    init {
        val serializePosts = prefs.getString(POST_PREFS_KEY, null)
        val posts = if(serializePosts != null) {
            Json.decodeFromString<List<Post>>(serializePosts)
        } else {
            emptyList()
        }
        data = MutableLiveData(posts)

    }

    var tempPost: Post? = null

    //TODO Не робает!!!!
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
        posts = posts.filterNot { id == it.postId }
    }

    override fun insert(content: String) {
        if (tempPost == null) {
            val id = ++postCounter
            Service.fillPostFavoriteList(id)
            posts = listOf(
                Post(
                    postId = id,
                    content = content,
                    authorName = "new author"
                )
            ) + posts
        } else {
            posts = posts.map {
                if (it.postId == tempPost!!.postId) tempPost!!.copy(content = content) else it
            }
            tempPost = null
        }
    }
}