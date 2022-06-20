package ru.netology.nmedia.data.impl

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.PostService.getSimpleDateFormat
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding



class PostAdapter(
    private val likeClicked: (Long) -> Unit
) : ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        println("onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("onBindViewHolder $position")
        val post = getItem(position)
        holder.bind(post)
    }

    inner class ViewHolder(
        private val binding: PostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.likesButton.setOnClickListener { likeClicked(post.postId) }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorNameTextView.text = post.authorName
                mainTextView.text = post.content
                postDateTextView.text = post.getSimpleDateFormat()
                shareCount.text =
                    "122" //ru.netology.nmedia.PostService.peopleCounter(post.shareCounter)
                likesCount.text =
                    "223" //ru.netology.nmedia.PostService.peopleCounter(post.likeCounter)
                mainTextLink.text = post.link ?: ""
                likesButton.setImageResource(
                    if (post.iLikeIt) {
                        println("color1")
                        R.drawable.ic_baseline_favorite_16dp
                    } else {
                        println("color2")
                        R.drawable.ic_baseline_favorite_border_16
                    }
                )
                //shareButton.setOnClickListener {viewModel.onRepostClick(post)}
                //likesButton.setOnClickListener { likeClicked(post.postId) }
            }
        }
    }

    private object DiffCallback: DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }

}