package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.databinding.PostContentBinding
import java.util.ArrayList

class PostContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundleIn = intent.getBundleExtra(RESULT_KEY_POST)

        val editText: String? = bundleIn?.getString(CONTENT)
        val videoLinkText: String? = bundleIn?.getString(VIDEO_LINK)

        with(binding) {
            edit.setText(editText ?: "")
            edit.requestFocus()
            editLink.setText(videoLinkText ?: "")
        }

        binding.ok.setOnClickListener() {
            val intent = Intent()
            val text = binding.edit.text
            val videoLink = binding.editLink.text
            if (text.isNullOrBlank() && videoLink.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val bundleOut = Bundle()
                val content: String? = text.toString().ifBlank {
                    null
                }
                val link: String? = videoLink.toString().ifBlank {
                    null
                }
                bundleOut.putString(CONTENT,content)
                bundleOut.putString(VIDEO_LINK, link)
                intent.putExtra(RESULT_KEY_POST, bundleOut)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }

        binding.cancel.setOnClickListener() {
            with(binding) {
                edit.text.clear()
                edit.clearFocus()
                editLink.text.clear()
            }
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }

    object ResultContract : ActivityResultContract<Post?, Bundle?>() {

        override fun createIntent(context: Context, input: Post?): Intent {
            val intent = Intent(context, PostContentActivity::class.java)
            val bundle = Bundle()
            bundle.putString(VIDEO_LINK, input?.video)
            bundle.putString(CONTENT, input?.content)
            intent.putExtra(RESULT_KEY_POST, bundle)
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Bundle? =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getBundleExtra(RESULT_KEY_POST)
            } else null
    }

    companion object {
        const val RESULT_KEY_POST = "postNewContent"
        const val VIDEO_LINK = "link"
        const val CONTENT = "content"
    }
}