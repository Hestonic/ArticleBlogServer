package ru.articleblog.database.articles_categories

import org.jetbrains.exposed.sql.Table
import ru.articleblog.database.categories.Categories

object ArticlesCategories : Table("articles_categories") {
    private val id = ArticlesCategories.integer("id")
    private val idArticle = ArticlesCategories.integer("id_article ")
    private val idCategory = ArticlesCategories.integer("id_category")
}