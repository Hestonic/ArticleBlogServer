package ru.articleblog.features.session

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.articleblog.database.token.Tokens

object SessionController {
    suspend fun getLogin(call: ApplicationCall) {
        val sessionReceiveRemote = call.receive<SessionReceiveRemote>()
        val login = Tokens.fetchLoginByToken(sessionReceiveRemote.token)
        call.respond(SessionResponseRemote(login = login))
    }
}