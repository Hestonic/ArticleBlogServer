package ru.articleblog.database.token

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table("tokens") {
    private val id = Tokens.integer("id")
    private val login = Tokens.varchar("login", 16)
    private val token = Tokens.varchar("token", 50)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[id] = tokenDTO.id
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }

    fun fetchLoginByToken(token: String): String {
        return transaction {
            Tokens.select { Tokens.token eq token }.map(::resultRowToLoginString).single()
        }
    }

    private fun resultRowToLoginString(row: ResultRow) = row[login]
}