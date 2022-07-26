package ru.netology.nmedia.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.activity.PostContentActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<PostViewModel>()

    @SuppressLint("IntentReset")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = PostAdapter(viewModel)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.shareContent.observe(this) {postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.urlContent.observe(this) {video ->
            if(video.isNullOrBlank()) return@observe
            val intent = Intent().apply {
                action = ACTION_VIEW
                data = Uri.parse(video)
            }
            val videoIntent = Intent.createChooser(intent, null)
            startActivity(videoIntent)
        }

        val newPostLauncher = registerForActivityResult(PostContentActivity.ResultContract) { result ->
            result ?: return@registerForActivityResult
            val newVideoLink = result.getString(PostContentActivity.VIDEO_LINK)
            val newContent = result.getString(PostContentActivity.CONTENT)
            viewModel.onSaveButtonClicked(newContent, newVideoLink)
        }

        viewModel.editPost.observe(this) { post ->
            newPostLauncher.launch(post)
        }

        binding.fab.setOnClickListener() {
            newPostLauncher.launch(null)
        }
    }
}