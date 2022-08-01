package ru.articleblog.features.articles

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.articleblog.database.articles.ArticleDTO
import ru.articleblog.database.articles.Articles
import ru.articleblog.database.articles_categories.ArticlesCategories
import ru.articleblog.database.articles_categories.ArticlesCategoriesDTO
import ru.articleblog.database.articles_info.ArticleInfoDTO
import ru.articleblog.database.articles_info.ArticlesInfo
import ru.articleblog.database.categories.Categories

object ArticlesController {
    suspend fun insertArticle(call: ApplicationCall) {
        val articleReceiveRemote = call.receive<ArticleReceiveRemote>()
        ArticlesInfo.insertArticleInfo(ArticleInfoDTO(id = 0, likes = 0, views = 0))
        ArticlesInfo.fetchArticleInfoLastRecord()?.let {
            Articles.insertArticle(
                ArticleDTO(
                    id = 0,
                    tittle = articleReceiveRemote.tittle,
                    text = articleReceiveRemote.text,
                    idArticleInfo = it.id,
                )
            )
        }

        Articles.fetchLastArticle()?.let { lastAddedArticle ->
            articleReceiveRemote.categories.forEach { idCategory ->
                ArticlesCategories.insert(
                    ArticlesCategoriesDTO(
                        id = 0,
                        idArticle = lastAddedArticle.id,
                        idCategory = idCategory
                    )
                )
            }
        }

        // TODO: Add return the response of a successful or unsuccessful addition of an article
    }

    suspend fun getArticles(call: ApplicationCall) {
        Articles.fetchAllArticles()?.let { articlesListDTO ->
            val articlesList = articlesListDTO.map {
                val articleInfo = ArticlesInfo.fetchArticleInfoById(it.idArticleInfo)
                val categories = ArticlesCategories.fetchCategoriesIdsByArticleId(it.id)
                    .map { idCategory -> Categories.fetchCategoryById(idCategory) }
                    .map { categoryDTO -> Category(id = categoryDTO.id, category = categoryDTO.category) }

                Article(
                    id = it.id,
                    tittle = it.tittle,
                    text = it.text,
                    categories = categories,
                    articleInfo = ArticleInfo(
                        id = articleInfo?.id ?: 0,
                        likes = articleInfo?.likes ?: 0,
                        views = articleInfo?.views ?: 0,
                    ),
                )
            }

            call.respond(ArticlesResponseRemote(articlesList))
        }
    }
}