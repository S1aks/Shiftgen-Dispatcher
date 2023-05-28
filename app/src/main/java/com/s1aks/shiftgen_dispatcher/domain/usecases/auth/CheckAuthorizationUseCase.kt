package com.s1aks.shiftgen_dispatcher.domain.usecases.auth

import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.RefreshData
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckAuthorizationUseCase(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val repository: Repository,
    private val localSecureStore: LocalSecureStore
) {
    suspend fun execute(): ResponseState<Boolean> {
        val login = localSecureStore.login
        val accessToken = localSecureStore.accessToken
        return if (!login.isNullOrEmpty() && !accessToken.isNullOrEmpty()) {
            val accessTokenCorrect = withContext(scope.coroutineContext) { repository.access() }
            if (accessTokenCorrect) {
                ResponseState.Success(true)
            } else {
                val refreshToken = localSecureStore.refreshToken
                return if (!refreshToken.isNullOrEmpty()) {
                    val tokensData =
                        withContext(scope.coroutineContext) {
                            repository.refresh(RefreshData(login, refreshToken))
                        }
                    if (tokensData.accessToken.isNotBlank() && tokensData.refreshToken.isNotBlank()) {
                        localSecureStore.accessToken = tokensData.accessToken
                        localSecureStore.refreshToken = tokensData.refreshToken
                        ResponseState.Success(true)
                    } else {
                        throw RuntimeException("Ошибка сохранения токена.")
                    }
                } else {
                    throw RuntimeException("Access токен ошибочный.")
                }
            }
        } else {
            ResponseState.Idle
        }
    }
}