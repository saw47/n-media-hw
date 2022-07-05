package ru.netology.nmedia

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.Group
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val adapter = PostAdapter(viewModel)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.postsRecyclerView.adapter = adapter

        binding.saveButton.setOnClickListener{
            with(binding.textContent) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
                clearFocus()
                hideKeyboard()
            }
        }

        binding.cancelButton.setOnClickListener{
            viewModel.onCancelClick()
            binding.textContent.clearFocus()
            binding.textContent.hideKeyboard()
        }


        viewModel.data.observe(this) { posts ->
           adapter.submitList(posts)
        }


        viewModel.currentPost.observe(this){ currentPost ->
            with(binding) {

                val content = currentPost?.content
                val author = currentPost?.authorName
                textContent.setText(content)

                if (content != null) {
                    group.visibility = View.VISIBLE
                    postNameToEdit.text = author
                    textContent.requestFocus()
                    textContent.showKeyboard()
                } else {
                    group.visibility = View.GONE
                    postNameToEdit.text = null
                    textContent.clearFocus()
                    textContent.hideKeyboard()
                }
            }
        }
    }
}