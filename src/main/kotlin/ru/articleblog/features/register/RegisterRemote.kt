package ru.articleblog.features.register

data class RegisterReceiveRemote(
    val login: String,
    val email: String,
    val password: String
)

data class RegisterResponseRemote(
    val token: String
)
