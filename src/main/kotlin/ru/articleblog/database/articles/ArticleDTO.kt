package ru.articleblog.database.articles

data class ArticleDTO(
    val id: Int,
    val tittle: String,
    val text: String,
    val idArticleInfo: Int,
)