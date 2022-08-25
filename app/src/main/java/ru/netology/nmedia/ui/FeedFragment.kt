package ru.netology.nmedia.ui

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by activityViewModels()
    private val gson = Gson()
    private val type = TypeToken.getParameterized(Post::class.java).type

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = PostAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
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
                action = ACTION_VIEW
                data = Uri.parse(video)
            }
            val videoIntent = Intent.createChooser(intent, null)
            startActivity(videoIntent)
        }

        viewModel.editPost.observe(viewLifecycleOwner) { post ->

            if (post.content.isNullOrBlank() && post.video.isNullOrBlank()) {
                return@observe
            } else {
                val direction = FeedFragmentDirections
                    .actionFeedFragmentToPostContentFragment(post.content, post.video)
                findNavController().navigate(direction)
            }
        }

        binding.fab.setOnClickListener() {
            val direction = FeedFragmentDirections
                .actionFeedFragmentToPostContentFragment(null, null)
            findNavController().navigate(direction)
        }

        viewModel.expandPost.observe(viewLifecycleOwner) { post ->
            val bundle = Bundle()
            val serializablePost = gson.toJson(post)
            bundle.putString(POST, serializablePost)
            findNavController().navigate(
                R.id.action_feedFragment_to_postFullSizeFragment,
                bundle
            )
        }

        return binding.root
    }

    companion object {
        const val POST = "post"
    }
}
