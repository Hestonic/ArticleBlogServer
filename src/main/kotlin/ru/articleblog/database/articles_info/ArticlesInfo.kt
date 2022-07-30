package ru.articleblog.database.articles_info

import org.jetbrains.exposed.sql.Table

object ArticlesInfo : Table("articles_info") {
    private val id = ArticlesInfo.integer("id")
    private val likes = ArticlesInfo.integer("likes")
    private val views = ArticlesInfo.integer("views")
}