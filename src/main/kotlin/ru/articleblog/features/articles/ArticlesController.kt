package ru.articleblog.features.articles

import io.ktor.http.*
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
                    title = articleReceiveRemote.title,
                    text = articleReceiveRemote.text,
                    idArticleInfo = it.id,
                    author = articleReceiveRemote.author,
                )
            )
        }

        Articles.fetchLastArticle()?.let { lastAddedArticle ->
            articleReceiveRemote.categories.forEach { idCategory ->
                ArticlesCategories.insertArticlesCategories(
                    ArticlesCategoriesDTO(
                        id = 0,
                        idArticle = lastAddedArticle.id,
                        idCategory = idCategory
                    )
                )
            }
        }
        call.respond(HttpStatusCode.Created)
    }

    suspend fun getArticles(call: ApplicationCall) {
        Articles.fetchAllArticles()?.let { articlesListDTO ->
            val articlesList = articlesListDTO.map { articleDTO ->
                val articleInfo = getArticleInfoById(articleDTO.idArticleInfo)
                val categories = getArticleCategoriesByArticleId(articleDTO.id)
                if (articleDTO.text.length > 200) {
                    val articleWithSubstring = articleDTO.copy(text = articleDTO.text.substring(0, 200).plus("..."))
                    mapArticle(articleWithSubstring, categories, articleInfo)
                } else mapArticle(articleDTO, categories, articleInfo)
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

    suspend fun deleteArticle(call: ApplicationCall) {
        call.parameters["id"]?.toInt()?.let { id ->
            Articles.deleteArticleById(id)
            call.respond(HttpStatusCode.OK)
        }
        call.respond(HttpStatusCode.BadRequest)
    }

    suspend fun updateArticle(call: ApplicationCall) {
        val articleReceiveRemote = call.receive<ArticleReceiveRemote>()
        ArticlesInfo.fetchArticleInfoLastRecord()?.let {
            call.parameters["id"]?.toInt()?.let { id ->
                val articleDto = ArticleDTO(
                    id = id,
                    title = articleReceiveRemote.title,
                    text = articleReceiveRemote.text,
                    idArticleInfo = it.id,
                    author = articleReceiveRemote.author,
                )
                Articles.updateArticleById(id, articleDto)
            }
            call.respond(HttpStatusCode.Created)
        }
        call.respond(HttpStatusCode.BadRequest)
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
        title = articleDTO.title,
        text = articleDTO.text,
        categories = categories,
        articleInfo = ArticleInfo(
            id = articleInfoDTO?.id ?: 0,
            likes = articleInfoDTO?.likes ?: 0,
            views = articleInfoDTO?.views ?: 0,
        ),
        author = articleDTO.author
    )
}