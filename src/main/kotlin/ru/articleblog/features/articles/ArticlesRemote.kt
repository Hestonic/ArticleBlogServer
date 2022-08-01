package ru.articleblog.features.articles

import kotlinx.serialization.Serializable

@Serializable
data class ArticleReceiveRemote(
    val tittle: String,
    val text: String,
    val categories: List<Int>,
)

@Serializable
data class ArticlesResponseRemote(
    val articlesList: List<Article>,
)

@Serializable
data class Article(
    val id: Int,
    val tittle: String,
    val text: String,
    val categories: List<Category>,
    val articleInfo: ArticleInfo,
)

@Serializable
data class Category(
    val id: Int,
    val category: String,
)

@Serializable
data class ArticleInfo(
    val id: Int,
    val likes: Int,
    val views: Int,
)