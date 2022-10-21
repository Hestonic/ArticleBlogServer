package ru.articleblog.features.session

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.articleblog.database.token.Tokens

object SessionController {
    // TODO: 401 unauthorized
    suspend fun getLogin(call: ApplicationCall) {
        val token = call.request.header(HttpHeaders.Authorization)
        if (token == null) call.respond(HttpStatusCode.BadRequest, "Invalid Token")
        else {
            val login = Tokens.fetchLoginByToken(token)
            call.respond(SessionResponseRemote(login = login))
        }
        call.respond(HttpStatusCode.BadRequest, "Invalid Token")
    }
}