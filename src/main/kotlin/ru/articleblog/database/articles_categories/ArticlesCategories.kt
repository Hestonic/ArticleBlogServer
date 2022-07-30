package ru.articleblog.database.articles_categories

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object ArticlesCategories : Table("articles_categories") {
    private val id = ArticlesCategories.integer("id")
    private val idArticle = ArticlesCategories.integer("id_article")
    private val idCategory = ArticlesCategories.integer("id_category")

    fun insert(articlesCategoriesDTO: ArticlesCategoriesDTO) {
        transaction {
            ArticlesCategories.insert {
                it[id] = articlesCategoriesDTO.id
                it[idArticle] = articlesCategoriesDTO.idArticle
                it[idCategory] = articlesCategoriesDTO.idCategory
            }
        }
    }
}