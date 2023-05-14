package com.s1aks.shiftgen_dispatcher.domain.usecases.auth

import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.RegisterData
import com.s1aks.shiftgen_dispatcher.data.entities.Structure
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SendRegisterDataUseCase(
    private val repository: Repository,
    private val localSecureStore: LocalSecureStore
) {
    suspend fun execute(registerData: RegisterData): ResponseState<Boolean> {
        val tokensData = withContext(Dispatchers.IO) {
            repository.insertStructure(Structure(name = registerData.structure))
            repository.register(registerData)
        }
        return if (tokensData.accessToken.isNotBlank() && tokensData.refreshToken.isNotBlank()) {
            localSecureStore.apply {
                login = registerData.login
                accessToken = tokensData.accessToken
                refreshToken = tokensData.refreshToken
            }
            ResponseState.Success(true)
        } else {
            ResponseState.Error(RuntimeException("Tokens mapping error"))
        }
    }
}