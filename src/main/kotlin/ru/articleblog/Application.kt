package ru.articleblog

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.articleblog.features.login.configureLoginRouting
import ru.articleblog.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureLoginRouting()
        configureSerialization()
    }.start(wait = true)
}
