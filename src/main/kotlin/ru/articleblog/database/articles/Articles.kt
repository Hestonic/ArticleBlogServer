package ru.articleblog.database.articles

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Articles : Table("articles") {
    private val id = Articles.integer("id")
    private val title = Articles.varchar("title", 50)
    private val text = Articles.text("text")
    private val idArticleInfo = Articles.integer("id_article_info")

    fun insertArticle(articlesDTO: ArticleDTO) {
        transaction {
            Articles.insert {
                it[id] = articlesDTO.id
                it[title] = articlesDTO.title
                it[text] = articlesDTO.text
                it[idArticleInfo] = articlesDTO.idArticleInfo
            }
        }
    }

    fun fetchLastArticle(): ArticleDTO? {
        return try {
            transaction {
                val articleModel = Articles.selectAll().last()
                resultRowToArticle(articleModel)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchAllArticles(): List<ArticleDTO>? {
        return try {
            transaction { Articles.selectAll().map(::resultRowToArticle) }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchArticleById(id: Int): ArticleDTO? {
        return try {
            transaction {
                Articles.select { Articles.id eq id }.map(::resultRowToArticle).singleOrNull()
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun resultRowToArticle(row: ResultRow) = ArticleDTO(
        id = row[id],
        title = row[title],
        text = row[text],
        idArticleInfo = row[idArticleInfo]
    )
}