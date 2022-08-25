package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.databinding.FragmentPostFullsizeBinding
import ru.netology.nmedia.util.Service
import ru.netology.nmedia.viewmodel.PostViewModel
import java.lang.Exception

class PostFullSizeFragment : Fragment() {

    private val viewModel: PostViewModel by activityViewModels()
    lateinit var post: Post
    private val gson = Gson()
    private val type = TypeToken.getParameterized(Post::class.java).type

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentPostFullsizeBinding.inflate(
            inflater,
            container,
            false
        )

        val serializablePost =  requireArguments().getString(FeedFragment.POST)
        if (serializablePost != null) {
            post = gson.fromJson(serializablePost, type)
        } else {
            throw Exception("wtf")
        }

        bind(post, binding)

        val popupMenu = PopupMenu(this.context, binding.optionsButton).apply {
            inflate(R.menu.options_post)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.remove -> {
                        viewModel.onRemoveClick(post.postId)
                        findNavController().popBackStack()
                        true
                    }
                    R.id.edit -> {
                        viewModel.onEditClick(post)
                        true
                    }
                    else -> false
                }
            }
        }

        viewModel.fullSizePost.observe(viewLifecycleOwner) { post ->
            bind(post, binding)
        }

        viewModel.urlContent.observe(viewLifecycleOwner) { video ->
            if (video.isNullOrBlank()) return@observe
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(video)
            }
            val videoIntent = Intent.createChooser(intent, null)
            startActivity(videoIntent)
        }

        viewModel.editPost.observe(viewLifecycleOwner) { post ->
            if (post.content.isNullOrBlank() && post.video.isNullOrBlank()) {
                return@observe
            } else {
                val direction = PostFullSizeFragmentDirections
                    .actionPostFullSizeFragmentToPostContentFragment(post.content, post.video)
                findNavController().navigate(direction)
            }
        }

        viewModel.shareContent.observe(viewLifecycleOwner) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.urlContent.observe(viewLifecycleOwner) { video ->
            if (video.isNullOrBlank()) return@observe
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(video)
            }
            val videoIntent = Intent.createChooser(intent, null)
            startActivity(videoIntent)
        }

        binding.likesButton.setOnClickListener {
            viewModel.onLikeClick(post)
        }

        binding.shareButton.setOnClickListener {
            viewModel.onRepostClick(post)
        }

        binding.optionsButton.setOnClickListener {
            popupMenu.show()
            binding.optionsButton.isChecked = true
        }

        popupMenu.setOnDismissListener() {
            binding.optionsButton.isChecked = false
        }

        binding.video.setOnClickListener {
            viewModel.onVideoLinkClicked(post)
        }

        return binding.root
    }

    private fun bind(post: Post, binding: FragmentPostFullsizeBinding) {
        this.post = post
        with(binding) {
            authorNameTextView.text = post.authorName
            mainTextView.text = post.content
            postDateTextView.text = Service.getSimpleDateFormat()
            shareButton.text = Service.peopleCounter(post.repostCounter)
            likesButton.text = Service.peopleCounter(post.favoriteCounter)
            mainTextLink.text = post.link ?: ""
            viewCount.text = Service.peopleCounter(post.viewCounter)
            likesButton.isChecked = post.favoriteSet.contains(FilePostRepository.user.userId)
            shareButton.isChecked = post.repostCounter >= 1
            if (!post.video.isNullOrBlank()) {
                video.visibility = View.VISIBLE
            }
        }
    }
}
