package ru.netology.nmedia.adapter

import android.view.LayoutInflater
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
    //private val likeClicked: (Long) -> Unit,
    //private val repostClicked: (Long) -> Unit
) : ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
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
            binding.likesButton.setOnClickListener { listener.onLikeClick(post.postId) }
            binding.shareButton.setOnClickListener { listener.onRepostClick(post.postId)}
        }


        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorNameTextView.text = post.authorName
                mainTextView.text = post.content
                postDateTextView.text = post.getSimpleDateFormat()
                shareCount.text = Service.peopleCounter(post.repostCounter)

                likesButton.text = Service.peopleCounter(post.favoriteCounter)

                mainTextLink.text = post.link ?: ""
                likesButton.isChecked = Service.likeCounter(post.postId) >= 1

//                likesButton.setButtonDrawable(
//                    if (Service.likeCounter(post.postId) >= 1) {
//                        R.drawable.ic_baseline_favorite_16dp
//                    } else {
//                        R.drawable.ic_baseline_favorite_border_16
//                    }
//                )
                optionsButton.setOnClickListener{popupMenu.show()}

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