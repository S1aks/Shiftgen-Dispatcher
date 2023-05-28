package com.s1aks.shiftgen_dispatcher.domain.usecases.auth

import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.Repository
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CheckAuthorizationUseCase(
    private val repository: Repository,
    private val localSecureStore: LocalSecureStore
) {
    suspend fun execute(): ResponseState<Boolean> {
        val login = localSecureStore.login
        val accessToken = localSecureStore.accessToken
        return if (!login.isNullOrEmpty() && !accessToken.isNullOrEmpty()) {
            val accessStatus = withContext(Dispatchers.IO) { repository.access() }
            if (accessStatus.isSuccess()) {
                ResponseState.Success(true)
            } else {
                if (accessStatus.value == 401) {
                    delay(200)
                    execute()
                } else {
                    throw RuntimeException("Ошибка аутентификации.")
                }
            }
        } else {
            ResponseState.Idle
        }
    }
}