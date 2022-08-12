package ru.articleblog.features.session

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSessionRouting() {

    routing {
        post("/api/session") {
            SessionController.getLogin(call)
        }
    }
}