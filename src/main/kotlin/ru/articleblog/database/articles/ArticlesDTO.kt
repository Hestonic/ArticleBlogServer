package ru.articleblog.database.articles

data class ArticlesDTO(
    val id: Int,
    val tittle: String,
    val text: String,
    val idArticleInfo: Int
)