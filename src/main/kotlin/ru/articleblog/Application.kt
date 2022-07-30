package ru.articleblog

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import ru.articleblog.features.articles.configureArticlesRouting
import ru.articleblog.features.login.configureLoginRouting
import ru.articleblog.features.register.configureRegisterRouting
import ru.articleblog.plugins.configureRouting
import ru.articleblog.plugins.configureSerialization

fun main() {
    Database.connect(
        "jdbc:mysql://localhost:3306/articleblog",
        "com.mysql.cj.jdbc.Driver",
        "root",
        "root"
    )

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureLoginRouting()
        configureRegisterRouting()
        configureArticlesRouting()
        configureSerialization()
    }.start(wait = true)
}
