package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.PostService.checkILikeItPost
import ru.netology.nmedia.User
import ru.netology.nmedia.UserService
import ru.netology.nmedia.data.PostRepo
import ru.netology.nmedia.data.impl.TemporaryObjects.generateNewRandomPost
import ru.netology.nmedia.data.impl.TemporaryObjects.posts


class InMemoryPostRepo : PostRepo {

    override val data: LiveData<List<Post>> = MutableLiveData<List<Post>>(
        posts
    )

    override fun like(post:Post) {
        if (!post.checkILikeItPost(TemporaryObjects.users[0])) {
            if (!TemporaryObjects.users[0].postByLikeMe.contains(post.postId)) {
                post.thoseWhoLikedIt.add(TemporaryObjects.users[0].userId)
                TemporaryObjects.users[0].postByLikeMe.add(post.postId)
                println("like ${post.likeCounter}  " +
                        "postId ${post.postId} " +
                        "userId ${TemporaryObjects.users[0].userId}" +
                        "userliked ${TemporaryObjects.users[0].postByLikeMe.size} \n") //TODO SERVICE
                UserService.printId(TemporaryObjects.users)
                println(TemporaryObjects.users.size)
                println(TemporaryObjects.posts.size)
            } else {
                throw Exception("Like function synchronization exception")
            }
        } else {
            if (TemporaryObjects.users[0].postByLikeMe.contains(post.postId)) {
                post.thoseWhoLikedIt.remove(TemporaryObjects.users[0].userId)
                TemporaryObjects.users[0].postByLikeMe.remove(post.postId)
            } else {
                throw Exception("Like function synchronization exception")
            }
        }
    }

    override fun repost(post: Post) {
//        val sharedPost = checkNotNull(data.value) {
//            "Data value can't be null"
//        }
//        sharedPost.sharePost(TemporaryObjects.postBrowsingUser)
//        data.value = sharedPost
    }

    fun Post.sharePost(user: User) {
        this.thoseWhoShareIt.add(user.userId)
        shareCounter = thoseWhoShareIt.size
    }
}