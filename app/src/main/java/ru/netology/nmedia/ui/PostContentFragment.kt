package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_post_content.*
import kotlinx.android.synthetic.main.post.*
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.FragmentPostContentBinding
import ru.netology.nmedia.util.Service
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class PostContentFragment : Fragment() {

    private val viewModel: PostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inArgs = arguments?.let { PostContentFragmentArgs.fromBundle(it) }

        val binding = FragmentPostContentBinding.inflate(
            inflater,
            container,
            false
        )

        with(binding) {
            edit.requestFocus()
            edit.setText(inArgs?.initialContent ?: "")
            editLink.setText(inArgs?.videoLinkContent ?: "")
        }

        binding.ok.setOnClickListener() {
            val text = if (binding.edit.text.isNullOrBlank()) null else binding.edit.text.toString()
            val videoLink =
                if (binding.editLink.text.isNullOrBlank()) null else binding.editLink.text.toString()

            if (text.isNullOrBlank() && videoLink.isNullOrBlank()) {
                //parentFragmentManager.popBackStack()
                return@setOnClickListener
            } else {
                viewModel.onSaveButtonClicked(newContent = text, newVideoLink = videoLink)
                //parentFragmentManager.popBackStack()
            }
            findNavController().popBackStack()
        }

        binding.cancel.setOnClickListener() {
            with(binding) {
                edit.text.clear()
                edit.clearFocus()
                editLink.text.clear()
                //parentFragmentManager.popBackStack()
            }
            findNavController().popBackStack()
        }

        return binding.root
    }

    companion object {
        const val RESULT_KEY_SAVE_POST = "requestKey"
        const val VIDEO_LINK = "link"
        const val CONTENT = "content"
    }
}