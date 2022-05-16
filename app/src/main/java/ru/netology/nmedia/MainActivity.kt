package ru.netology.nmedia

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.PostService.checkILikeItPost
import ru.netology.nmedia.PostService.getSimpleDateFormat
import ru.netology.nmedia.PostService.likePost
import ru.netology.nmedia.PostService.sharePost
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val author = User(
            userName = "Ivan Ivanov"
        )
        val anyUser = User(
            userName = "Ivan Petrov"
        )
        val post = Post(
            authorName = author.userName,
            content = "Двое гуляли в лесу, держась за руки. Они были вдвоём и это им нравилось. " +
                    "Кружась, на их голову падала жёлтая листва, была осень. " +
                    "Внезапно листва сменилась белым снегом. Он блестел на холодном солнце, " +
                    "а двоим было тепло. Любовь сильнее зимы. Снег растаял, настало время весны. " +
                    "А пара всё бродила вокруг да около. Это взволновало белок, которые подумали," +
                    " что двое людей покушаются на их запасы. Спелые до черноты шишки полетели " +
                    "в людей, а тем было смешно. Но вот град шишек опять сменился жёлтыми листьями." +
                    " Осень, - сказал парень, прищурившись, - пора домой, а то завтра в школу. " +
                    "Ещё уроки не сделаны.Это были его первые слова за всю прогулку.Шурх! " +
                    "Шуршал ёжик в палой листве. Шурх! Он копал нору на зиму, чтобы спрятать" +
                    " в ней свои колючки. Те были очень ему дороги, с тех пор, как выросли."
        )

        with(binding) {
            authorNameTextView.text = post.authorName
            mainTextView.text = post.content
            postDateTextView.text = post.getSimpleDateFormat()
            likesCount.text = post.stringLikeCounter
            shareCount.text = post.stringShareCounter
            if (post.checkILikeItPost(anyUser)) likesButton.setImageResource(R.drawable.ic_baseline_favorite_16dp)
        }

        binding.likesButton.setOnClickListener {
            post.likePost(anyUser)
            likesCount.text = PostService.peopleCounter(post.likeCounter)

            likesButton.setImageResource(
                if (post.checkILikeItPost(anyUser)) {
                    R.drawable.ic_baseline_favorite_16dp
                } else {
                    R.drawable.ic_baseline_favorite_border_16
                }
            )
        }

        shareButton.setOnClickListener() {
            post.sharePost(anyUser)
            shareCount.text = PostService.peopleCounter(post.shareCounter)
        }
    }
}