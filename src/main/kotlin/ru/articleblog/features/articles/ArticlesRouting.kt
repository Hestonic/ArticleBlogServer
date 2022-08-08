package ru.articleblog.features.articles

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureArticlesRouting() {

    routing {
        get("/api/articles") {
            ArticlesController.getArticles(call)
        }

        get("/api/articles/{id}") {
            ArticlesController.getArticle(call)
        }

        post("/api/articles/add") {
            ArticlesController.insertArticle(call)
        }

        get("/api/categories") {
            ArticlesController.getAllCategories(call)
        }
    }
}