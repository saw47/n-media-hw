package ru.netology.nmedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.PostService.checkILikeItPost
import ru.netology.nmedia.PostService.getSimpleDateFormat
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding

internal class PostAdapter(
    private val posts: List<Post>,
    private val likeClicked: (Post) -> Unit
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    inner class ViewHolder(
        private val binding: PostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) = with(binding) {
            authorNameTextView.text = post.authorName
            mainTextView.text = post.content
            postDateTextView.text = post.getSimpleDateFormat()
            shareCount.text = ru.netology.nmedia.PostService.peopleCounter(post.shareCounter)
            likesCount.text = ru.netology.nmedia.PostService.peopleCounter(post.likeCounter)
            mainTextLink.text = post.link ?: ""

            if (!post.checkILikeItPost(TemporaryObjects.users[0])) likesButton
                .setImageResource(R.drawable.ic_baseline_favorite_16dp)

            likesButton.setImageResource(
                if (post.checkILikeItPost(TemporaryObjects.users[0])) {
                    println("color1")
                    R.drawable.ic_baseline_favorite_16dp
                } else {
                    println("color2")
                    R.drawable.ic_baseline_favorite_border_16
                }
            )

            //likesButton.setOnClickListener { likeClicked(post) }
            binding.likesButton.setOnClickListener{ likeClicked(post) }
            //shareButton.setOnClickListener {viewModel.onRepostClick(post)}
        }
    }

}