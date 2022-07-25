package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.util.Service
import ru.netology.nmedia.util.Service.getSimpleDateFormat
import ru.netology.nmedia.databinding.PostBinding

class PostAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        Service.setAsView(post.postId)
        holder.bind(post)
    }

    inner class ViewHolder(
        private val binding: PostBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.optionsButton).apply {
                inflate(R.menu.options_post)

                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClick(post.postId)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClick(post)

                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.likesButton.setOnClickListener {
                println("like clicked")
                listener.onLikeClick(post.postId)
            }
            binding.shareButton.setOnClickListener {
                println("share clicked")
                listener.onRepostClick(post)
            }
            binding.optionsButton.setOnClickListener {
                popupMenu.show()
                binding.optionsButton.isChecked = true
            }
            popupMenu.setOnDismissListener() {
                binding.optionsButton.isChecked = false
            }

            binding.video.setOnClickListener {
                println("video clicked")
                listener.onVideoLinkClicked(post)
                }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                authorNameTextView.text = post.authorName
                mainTextView.text = post.content
                postDateTextView.text = post.getSimpleDateFormat()
                shareButton.text = Service.peopleCounter(post.repostCounter)
                likesButton.text = Service.peopleCounter(post.favoriteCounter)
                mainTextLink.text = post.link ?: ""
                viewCount.text = Service.peopleCounter(Service.viewCount(post.postId))
                likesButton.isChecked = Service.likeCounter(post.postId) >= 1
                shareButton.isChecked = Service.repostCount(post.postId) >= 1
                if (post.video != null) {
                    video.visibility = View.VISIBLE
                }
            }

        }
    }


    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }

}