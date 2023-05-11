package com.s1aks.shiftgen_dispatcher.domain.usecases.auth

import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckAuthorizationUseCase(
    private val repository: Repository,
    private val localSecureStore: LocalSecureStore
) {
    suspend fun execute(): ResponseState<Boolean> {
        val accessToken = localSecureStore.accessToken
        if (accessToken != null) {
            val accessTokenCorrect = withContext(Dispatchers.IO) { repository.access(accessToken) }
            if (accessTokenCorrect) {
                return ResponseState.Success(true)
            } else {
                val refreshToken = localSecureStore.refreshToken
                return if (refreshToken != null) {
                    val tokensData =
                        withContext(Dispatchers.IO) { repository.refresh(refreshToken) }
                    if (tokensData.accessToken.isNotBlank() && tokensData.refreshToken.isNotBlank()) {
                        localSecureStore.accessToken = tokensData.accessToken
                        localSecureStore.refreshToken = tokensData.refreshToken
                        ResponseState.Success(true)
                    } else {
                        ResponseState.Error(RuntimeException("Tokens mapping error"))
                    }
                } else {
                    ResponseState.Error(RuntimeException("Access token incorrect"))
                }
            }
        } else {
            return ResponseState.Success(false)
        }
    }
}