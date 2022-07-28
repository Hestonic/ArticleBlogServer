package ru.articleblog.database.token

data class TokenDTO(
    val id: Int,
    val login: String,
    val token: String
)