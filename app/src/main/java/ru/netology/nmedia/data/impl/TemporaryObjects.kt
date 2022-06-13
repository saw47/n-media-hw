package ru.netology.nmedia.data.impl

import ru.netology.nmedia.Post
import ru.netology.nmedia.PostService
import ru.netology.nmedia.User

object TemporaryObjects {
    val postBrowsingUser = User(
        userName = "Ivan Garcia-Ramirez",
    )

    val somePost = Post(
        authorName = "Egor Letov",
        content = "Сквозь ветхую крышу текла озорная заря\n" +
                "   текла безмятежно и густо\n" +
                "Сквозь ветхую крышу на запятнанные простыни\n" +
                "На больничные подушки\n" +
                "На большие подоконники\n" +
                "На столы и подоконники\n" +
                "Печальные большие словно трещины в стакане\n" +
                "Немыслимые словно отрывной календарь.\n",
        link = "https://www.gr-oborona.ru"
    )
}