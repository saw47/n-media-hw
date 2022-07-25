package ru.netology.nmedia.activity

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.IntentHandlerBinding
import ru.netology.nmedia.databinding.PostContentBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class PostContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostContentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val editText: String? = if (intent.extras != null) {
            intent.extras!!.get(RESULT_KEY).toString()
        } else {
            null
        }

        with(binding.edit) {
            setText(editText)
            requestFocus()
        }

        binding.ok.setOnClickListener() {
            val intent = Intent()
            val text = binding.edit.text
            if (text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = text.toString()
                intent.putExtra(RESULT_KEY, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }

        binding.cancel.setOnClickListener() {
            with(binding.edit) {
                text.clear()
                clearFocus()
            }
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }

    object ResultContract : ActivityResultContract<String?, String?>() {

        override fun createIntent(context: Context, input: String?): Intent {
            val intent = Intent(context, PostContentActivity::class.java)
            intent.putExtra(RESULT_KEY, input ?:"")
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?) =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(RESULT_KEY)
            } else null
    }

    companion object {
        const val RESULT_KEY = "postNewContent"
    }
}