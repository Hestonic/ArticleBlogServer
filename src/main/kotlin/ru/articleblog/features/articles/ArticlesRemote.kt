package ru.articleblog.features.articles

import kotlinx.serialization.Serializable

@Serializable
data class ArticleReceiveRemote(
    val title: String,
    val text: String,
    val categories: List<Int>,
    val author: String,
)

@Serializable
data class ArticlesResponseRemote(
    val articlesList: List<Article>,
)

@Serializable
data class Article(
    val id: Int,
    val title: String,
    val text: String,
    val categories: List<Category>,
    val articleInfo: ArticleInfo,
    val author: String,
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