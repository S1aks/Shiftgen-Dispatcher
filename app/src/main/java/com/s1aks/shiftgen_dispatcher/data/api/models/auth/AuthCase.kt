package com.s1aks.shiftgen_dispatcher.data.api.models.auth

import com.s1aks.shiftgen_dispatcher.data.api.ApiService.Companion.BASE_URL

interface AuthCase {

    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun refresh(refreshRequest: RefreshRequest): LoginResponse
    suspend fun register(registerRequest: RegisterRequest): RegisterResponse

    companion object {
        const val LOGIN = "${BASE_URL}/login"
        const val REFRESH = "${BASE_URL}/refresh"
        const val REGISTER = "${BASE_URL}/register"
    }
}