package com.s1aks.shiftgen_dispatcher.data.api.modules.auth

import com.s1aks.shiftgen_dispatcher.data.api.ApiService.Companion.BASE_URL
import io.ktor.http.HttpStatusCode

interface AuthCase {

    suspend fun access(): HttpStatusCode
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun refresh(refreshRequest: RefreshRequest): LoginResponse
    suspend fun register(registerRequest: RegisterRequest): RegisterResponse

    companion object {
        const val ACCESS_URL = "${BASE_URL}/auth/access"
        const val LOGIN_URL = "${BASE_URL}/auth/login"
        const val REFRESH_URL = "${BASE_URL}/auth/refresh"
        const val REGISTER_URL = "${BASE_URL}/auth/register"
    }
}