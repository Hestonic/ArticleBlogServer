package ru.articleblog.features.login

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureLoginRouting() {

    routing {
        post("/api/login") {
            LoginController.performLogin(call)
        }
    }
}