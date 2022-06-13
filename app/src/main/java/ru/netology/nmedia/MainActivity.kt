package ru.netology.nmedia

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import ru.netology.nmedia.PostService.checkILikeItPost
import ru.netology.nmedia.PostService.getSimpleDateFormat
import ru.netology.nmedia.PostService.sharePost
import ru.netology.nmedia.data.impl.TemporaryObjects
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) {post ->
            binding.render(post)
        }

        binding.likesButton.setOnClickListener {
            viewModel.onLikeClick()
        }

        binding.shareButton.setOnClickListener{
            viewModel.onRepostClick()
        }
    }

    private fun ActivityMainBinding.render(post:Post) {
        authorNameTextView.text = post.authorName
        mainTextView.text = post.content
        postDateTextView.text = post.getSimpleDateFormat()
        shareCount.text = PostService.peopleCounter(post.shareCounter)
        likesCount.text = PostService.peopleCounter(post.likeCounter)
        mainTextLink.text = post.link ?: ""
        if (post.checkILikeItPost(TemporaryObjects.postBrowsingUser)) likesButton
            .setImageResource(R.drawable.ic_baseline_favorite_16dp)

        likesButton.setImageResource(
            if (post.checkILikeItPost(TemporaryObjects.postBrowsingUser)) {
                R.drawable.ic_baseline_favorite_16dp
            } else {
                R.drawable.ic_baseline_favorite_border_16
            }
        )
    }
}