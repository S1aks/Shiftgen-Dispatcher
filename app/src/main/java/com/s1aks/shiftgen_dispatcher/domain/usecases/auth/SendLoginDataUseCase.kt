package com.s1aks.shiftgen_dispatcher.domain.usecases.auth

import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.Repository
import com.s1aks.shiftgen_dispatcher.domain.models.LoginData

class SendLoginDataUseCase(
    private val repository: Repository,
    private val localSecureStore: LocalSecureStore
) {
    suspend fun execute(loginData: LoginData): ResponseState<Boolean> {
        val tokensData = repository.login(loginData)
        return if (tokensData.accessToken.isNotBlank() && tokensData.refreshToken.isNotBlank()) {
            localSecureStore.accessToken = tokensData.accessToken
            localSecureStore.refreshToken = tokensData.refreshToken
            ResponseState.Success(true)
        } else {
            ResponseState.Error(RuntimeException("Tokens mapping error"))
        }
    }
}