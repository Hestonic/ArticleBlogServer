package ru.articleblog.database.token

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import ru.articleblog.database.users.Users

object Tokens : Table("tokens") {
    private val id = Users.integer("id")
    private val login = Users.varchar("login", 16)
    private val token = Users.varchar("token", 50)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[id] = tokenDTO.id
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }
}