package com.s1aks.shiftgen_dispatcher.data.api.modules.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val login: String,
    val email: String,
    val password: String,
    val group: Int,
    val structureId: Int,
    val dispatcherPin: String
)

@Serializable
data class RegisterResponse(
    val accessToken: String,
    val refreshToken: String
)