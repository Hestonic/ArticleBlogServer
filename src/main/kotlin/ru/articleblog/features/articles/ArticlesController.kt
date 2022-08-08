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
    }

    suspend fun getArticles(call: ApplicationCall) {
        Articles.fetchAllArticles()?.let { articlesListDTO ->
            val articlesList = articlesListDTO.map { articleDTO ->
                val articleInfo = getArticleInfoById(articleDTO.idArticleInfo)
                val categories = getArticleCategoriesByArticleId(articleDTO.id)
                mapArticle(articleDTO, categories, articleInfo)
            }
            call.respond(ArticlesResponseRemote(articlesList))
        }
    }

    suspend fun getArticle(call: ApplicationCall) {
        call.parameters["id"]?.toInt()?.let { id ->
            Articles.fetchArticleById(id)?.let { articleDTO ->
                val articleInfo = getArticleInfoById(articleDTO.idArticleInfo)
                val categories = getArticleCategoriesByArticleId(articleDTO.id)
                val article = mapArticle(articleDTO, categories, articleInfo)
                call.respond(article)
            }
        }
    }

    suspend fun getAllCategories(call: ApplicationCall) {
        val listCategoriesDTO = Categories.fetchAllCategories()
        val allCategories = listCategoriesDTO.map { Category(id = it.id, category = it.category) }
        call.respond(allCategories)
    }

    private fun getArticleInfoById(idArticleInfo: Int) = ArticlesInfo.fetchArticleInfoById(idArticleInfo)

    private fun getArticleCategoriesByArticleId(articleId: Int) =
        ArticlesCategories.fetchCategoriesIdsByArticleId(articleId)
            .map { idCategory -> Categories.fetchCategoryById(idCategory) }
            .map { categoryDTO -> Category(id = categoryDTO.id, category = categoryDTO.category) }

    private fun mapArticle(
        articleDTO: ArticleDTO, categories: List<Category>, articleInfoDTO: ArticleInfoDTO?,
    ): Article = Article(
        id = articleDTO.id,
        tittle = articleDTO.tittle,
        text = articleDTO.text,
        categories = categories,
        articleInfo = ArticleInfo(
            id = articleInfoDTO?.id ?: 0,
            likes = articleInfoDTO?.likes ?: 0,
            views = articleInfoDTO?.views ?: 0,
        ),
    )
}