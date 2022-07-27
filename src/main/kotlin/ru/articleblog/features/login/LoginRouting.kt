package ru.articleblog.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.articleblog.features.cache.InMemoryCache
import ru.articleblog.features.cache.TokenCache
import java.util.UUID

fun Application.configureLoginRouting() {

    routing {
        get("/login") {
            val receive = call.receive(LoginReceiveRemote::class)
            if (InMemoryCache.userList.map { it.login }.contains(receive.login)) {
                val token = UUID.randomUUID().toString()
                InMemoryCache.token.add(TokenCache(login = receive.login, token = token))
                call.respond(LoginResponseRemote(token = token))
                return@get
            }

            call.respond(HttpStatusCode.BadRequest)
        }
    }
}