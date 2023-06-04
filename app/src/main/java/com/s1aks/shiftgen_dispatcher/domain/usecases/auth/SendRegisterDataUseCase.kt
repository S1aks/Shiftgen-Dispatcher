package com.s1aks.shiftgen_dispatcher.domain.usecases.auth

import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.RegisterData
import com.s1aks.shiftgen_dispatcher.data.entities.Structure
import com.s1aks.shiftgen_dispatcher.domain.Repository
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SendRegisterDataUseCase(
    private val repository: Repository,
    private val localSecureStore: LocalSecureStore
) {
    suspend fun execute(registerData: RegisterData): ResponseState<Boolean> {
        val structureId: Int = withContext(Dispatchers.IO) {
            repository.getStructures()[registerData.structure]
                ?: withContext(Dispatchers.IO) {
                    repository.insertStructure(
                        Structure(
                            0,
                            registerData.structure,
                            dispatcherPin = registerData.dispatcherPin
                        )
                    )
                    delay(200)
                    repository.getStructures()[registerData.structure]
                        ?: throw RuntimeException("Ошибка создания структуры.")
                }
        }
        val tokensData = withContext(Dispatchers.IO) {
            repository.register(registerData, structureId)
        }
        return if (tokensData.accessToken.isNotBlank() && tokensData.refreshToken.isNotBlank()) {
            localSecureStore.apply {
                login = registerData.login
                accessToken = tokensData.accessToken
                refreshToken = tokensData.refreshToken
                this.structureId = structureId
            }
            ResponseState.Success(true)
        } else {
            localSecureStore.clear()
            throw RuntimeException("Ошибка регистрации.")
        }
    }
}