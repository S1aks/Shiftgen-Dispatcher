package com.s1aks.shiftgen_dispatcher.data.remote_api.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val login: String,
    val email: String,
    val password: String,
    val accessGroup: Int,
    val structureId: Int
)

@Serializable
data class RegisterResponse(
    val accessToken: String,
    val refreshToken: String
)