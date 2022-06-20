package ru.netology.nmedia

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import ru.netology.nmedia.PostService.getSimpleDateFormat
import ru.netology.nmedia.data.impl.PostAdapter
import ru.netology.nmedia.data.impl.TemporaryObjects
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    val adapter = PostAdapter(
            likeClicked = { id ->
            viewModel.onLikeClick(id)
        }
    )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(this) { posts ->
           adapter.submitList(posts)
        }
    }
}