package ru.articleblog.database.articles

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Articles : Table("articles") {
    private val id = Articles.integer("id")
    private val tittle = Articles.varchar("tittle", 50)
    private val text = Articles.text("text")
    private val idArticleInfo = Articles.integer("id_article_info")

    fun insertArticle(articlesDTO: ArticleDTO) {
        transaction {
            Articles.insert {
                it[id] = articlesDTO.id
                it[tittle] = articlesDTO.tittle
                it[text] = articlesDTO.text
                it[idArticleInfo] = articlesDTO.idArticleInfo
            }
        }
    }

    fun fetchLastArticle(): ArticleDTO? {
        return try {
            transaction {
                val articleModel = Articles.selectAll().last()
                ArticleDTO(
                    id = articleModel[Articles.id],
                    text = articleModel[text],
                    tittle = articleModel[tittle],
                    idArticleInfo = articleModel[idArticleInfo],
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}