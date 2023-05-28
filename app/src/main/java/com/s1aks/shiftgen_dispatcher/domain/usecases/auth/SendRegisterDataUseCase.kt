package com.s1aks.shiftgen_dispatcher.domain.usecases.auth

import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.RegisterData
import com.s1aks.shiftgen_dispatcher.data.entities.Structure
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SendRegisterDataUseCase(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val repository: Repository,
    private val localSecureStore: LocalSecureStore
) {
    suspend fun execute(registerData: RegisterData): ResponseState<Boolean> {
        val structureId: Int = withContext(scope.coroutineContext) {
            repository.getStructures()[registerData.structure]
                ?: runBlocking {
                    repository.insertStructure(Structure(0, registerData.structure))
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
            }
            ResponseState.Success(true)
        } else {
            throw RuntimeException("Ошибка регистрации.")
        }
    }
}