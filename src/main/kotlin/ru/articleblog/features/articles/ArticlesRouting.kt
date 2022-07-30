package ru.articleblog.features.articles

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureArticlesRouting() {

    routing {
        get("/api/articles") {
            ArticlesController.getArticles(call)
        }

        post("/api/articles/add") {
            ArticlesController.insertArticle(call)
        }
    }
}