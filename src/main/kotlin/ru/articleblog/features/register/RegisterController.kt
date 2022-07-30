package ru.articleblog.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.articleblog.database.token.TokenDTO
import ru.articleblog.database.token.Tokens
import ru.articleblog.database.users.UserDTO
import ru.articleblog.database.users.Users
import java.util.*

object RegisterController {

    suspend fun registerNewUser(call: ApplicationCall) {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        val userDTO = Users.fetchUser(registerReceiveRemote.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } else {
            val token = UUID.randomUUID().toString()

            try {
                Users.insert(
                    UserDTO(
                        login = registerReceiveRemote.login,
                        password = registerReceiveRemote.password
                    )
                )
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Can't create user ${e.localizedMessage}")
            }

            Tokens.insert(TokenDTO(id = 0, login = registerReceiveRemote.login, token = token))
            call.respond(RegisterResponseRemote(token = token))
        }
    }
}