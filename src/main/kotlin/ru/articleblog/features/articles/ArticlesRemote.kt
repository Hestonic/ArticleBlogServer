package ru.articleblog.features.articles

import kotlinx.serialization.Serializable

@Serializable
data class ArticleReceiveRemote(
    val tittle: String,
    val text: String,
    val categories: List<Int>,
)
