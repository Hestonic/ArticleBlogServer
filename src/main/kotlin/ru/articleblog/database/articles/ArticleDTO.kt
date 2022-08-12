package ru.articleblog.database.articles

data class ArticleDTO(
    val id: Int,
    val title: String,
    val text: String,
    val idArticleInfo: Int,
)