package com.s1aks.shiftgen_dispatcher.domain.usecases.auth

import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.LoginData
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SendLoginDataUseCase(
    private val repository: Repository,
    private val localSecureStore: LocalSecureStore
) {
    suspend fun execute(loginData: LoginData): ResponseState<Boolean> {
        val tokensData = withContext(Dispatchers.IO) { repository.login(loginData) }
        return if (tokensData.accessToken.isNotBlank() && tokensData.refreshToken.isNotBlank()) {
            localSecureStore.apply {
                login = loginData.login
                accessToken = tokensData.accessToken
                refreshToken = tokensData.refreshToken
                structureId = withContext(Dispatchers.IO) { repository.getStructureId() }
            }
            ResponseState.Success(true)
        } else {
            throw RuntimeException("Ошибка авторизации.")
        }
    }
}