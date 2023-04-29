package com.s1aks.shiftgen_dispatcher.domain.usecases.auth

import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.Repository
import com.s1aks.shiftgen_dispatcher.domain.models.RegisterData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SendRegisterDataUseCase(
    private val repository: Repository,
    private val localSecureStore: LocalSecureStore
) {
    suspend fun execute(registerData: RegisterData): ResponseState<Boolean> {
        val tokensData = withContext(Dispatchers.IO) {
            repository.register(registerData)
        }
        return if (tokensData.accessToken.isNotBlank() && tokensData.refreshToken.isNotBlank()) {
            localSecureStore.accessToken = tokensData.accessToken
            localSecureStore.refreshToken = tokensData.refreshToken
            ResponseState.Success(true)
        } else {
            ResponseState.Error(RuntimeException("Tokens mapping error"))
        }
    }
}