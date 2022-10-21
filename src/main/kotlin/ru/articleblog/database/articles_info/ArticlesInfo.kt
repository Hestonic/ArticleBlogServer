package ru.articleblog.database.articles_info

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object ArticlesInfo : Table("article_info") {
    private val id = ArticlesInfo.integer("id")
    private val likes = ArticlesInfo.integer("likes")
    private val views = ArticlesInfo.integer("views")

    fun insertArticleInfo(articleInfoDTO: ArticleInfoDTO) {
        transaction {
            ArticlesInfo.insert {
                it[id] = articleInfoDTO.id
                it[likes] = articleInfoDTO.likes
                it[views] = articleInfoDTO.views
            }
        }
    }

    fun fetchArticleInfoLastRecord(): ArticleInfoDTO? {
        return try {
            transaction {
                val articleInfoModel = ArticlesInfo.selectAll().last()
                resultRowToArticleInfoDto(articleInfoModel)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchArticleInfoById(id: Int): ArticleInfoDTO? {
        return try {
            transaction {
                ArticlesInfo.select { ArticlesInfo.id eq id }.map(::resultRowToArticleInfoDto).singleOrNull()
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun resultRowToArticleInfoDto(row: ResultRow) = ArticleInfoDTO(
        id = row[id],
        likes = row[likes],
        views = row[views],
    )
}