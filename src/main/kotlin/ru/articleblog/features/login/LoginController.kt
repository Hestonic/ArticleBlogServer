package ru.articleblog.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.articleblog.database.token.TokenDTO
import ru.articleblog.database.token.Tokens
import ru.articleblog.database.users.Users
import java.util.*

object LoginController {

    suspend fun performLogin(call: ApplicationCall) {
        val loginReceiveRemote = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUser(loginReceiveRemote.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            if (userDTO.password == loginReceiveRemote.password) {
                val token = UUID.randomUUID().toString()
                Tokens.insert(TokenDTO(id = 0, login = loginReceiveRemote.login, token = token))
                call.respond(LoginResponseRemote(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }
}