package ru.articleblog.features.register

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRegisterRouting() {

    routing {
        post("/api/register") {
            RegisterController.registerNewUser(call)
        }
    }
}