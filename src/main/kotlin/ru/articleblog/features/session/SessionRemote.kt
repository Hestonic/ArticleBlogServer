package ru.articleblog.features.session

import kotlinx.serialization.Serializable

@Serializable
data class SessionReceiveRemote(
    val token: String,
)

@Serializable
data class SessionResponseRemote(
    val login: String,
)