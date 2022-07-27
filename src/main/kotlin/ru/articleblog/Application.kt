package ru.articleblog

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.articleblog.features.login.configureLoginRouting
import ru.articleblog.features.register.configureRegisterRouting
import ru.articleblog.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureLoginRouting()
        configureRegisterRouting()
        configureSerialization()
    }.start(wait = true)
}
