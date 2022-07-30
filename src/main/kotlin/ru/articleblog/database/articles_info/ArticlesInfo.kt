package ru.articleblog.database.articles_info

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
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
                ArticleInfoDTO(
                    id = articleInfoModel[ArticlesInfo.id],
                    likes = articleInfoModel[likes],
                    views = articleInfoModel[views],
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}