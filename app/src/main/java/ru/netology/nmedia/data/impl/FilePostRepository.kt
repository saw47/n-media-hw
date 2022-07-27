package ru.netology.nmedia.data.impl

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.util.User
import kotlin.properties.Delegates

class FilePostRepository(private val application: Application) : PostRepository {
    companion object {
        private const val NEXT_ID_PREFS_KEY = "id"
        private const val FILE_NAME = "posts.json"

        //условный пользователь, который просматривает посты
        val user = User(
            userId = 1L,
            userName = "Random User"
        )
    }
    //временный пост для создания/изменения
    var tempPost: Post? = null

    //хранение идентификатора номера последнего поста
    private val context: Context = application.applicationContext
    private val lastIndex: SharedPreferences = context
        .getSharedPreferences(NEXT_ID_PREFS_KEY, Context.MODE_PRIVATE)

    init {
        if (lastIndex.getLong(NEXT_ID_PREFS_KEY,-1L) < 0L) {
            val editPrefs = lastIndex.edit()
            editPrefs.putLong(NEXT_ID_PREFS_KEY,0L)
            editPrefs.apply()
        }
    }


    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            application.openFileOutput(
                FILE_NAME, Context.MODE_PRIVATE
            ).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val postsFile = application.filesDir.resolve(FILE_NAME)
        val posts: List<Post> = if (postsFile.exists()) {
            val inputStream = application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use { gson.fromJson(it, type) }
        } else emptyList()
        data = MutableLiveData(posts)
    }

    override fun like(id: Long) {
        posts = posts.map { post ->
            if (post.postId == id) {
                val favorites = if (post.favoriteSet.contains(user.userId)) {
                    post.favoriteSet.minus(user.userId)
                } else {
                    post.favoriteSet.plus(user.userId)
                }
                post.copy(
                    favoriteSet = favorites,
                    favoriteCounter = favorites.filter { it == user.userId }.size
                )
            } else post
        }
    }

    override fun repost(id: Long) {
        posts = posts.map { post ->
            if (post.postId == id) {
                post.copy(repostCounter = post.repostCounter + 1)
            } else {
                post
            }
        }
    }

    override fun delete(id: Long) {
        posts = posts.filterNot { id == it.postId }
    }

    override fun insert(content: String?, videoLink: String?) {
        if (tempPost == null) {
            val index = lastIndex.getLong(NEXT_ID_PREFS_KEY, 0L) + 1L
            posts = listOf(
                Post(
                    postId = index,
                    content = content,
                    authorName = "new author",
                    video = videoLink
                )
            ) + posts
            val editPrefs = lastIndex.edit()
            editPrefs.putLong(NEXT_ID_PREFS_KEY,index)
            editPrefs.apply()
        } else {
            posts = posts.map {
                if (it.postId == tempPost!!.postId) tempPost!!.copy(
                    content = content,
                    video = videoLink
                ) else it
            }
            tempPost = null
        }
    }
}