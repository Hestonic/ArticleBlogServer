package ru.articleblog.database.articles

import org.jetbrains.exposed.sql.Table
import ru.articleblog.database.categories.Categories

object Articles : Table("articles") {
    private val id = Categories.integer("id")
    private val tittle = Categories.varchar("tittle", 50)
    private val text = Categories.text("text")
    private val idArticleInfo = Categories.integer("id_article_info")
}