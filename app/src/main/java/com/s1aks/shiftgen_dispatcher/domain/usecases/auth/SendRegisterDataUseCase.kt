package com.s1aks.shiftgen_dispatcher.domain.usecases.auth

import android.content.res.Resources.NotFoundException
import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Groups
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class SendRegisterDataUseCase(
    private val repository: Repository,
    private val localSecureStore: LocalSecureStore
) {
    suspend fun execute(
        login: String, email: String, password: String, group: String, structure: String
    ): ResponseState<Boolean> {
        val tokensData = withContext(Dispatchers.IO) {
            val groupNumber: Deferred<Int> = async {
                Groups.values().find { it.groupName == group }?.ordinal
                    ?: throw NotFoundException("Error! Group not found.")
            }
            val structureId: Deferred<Int> = async { 0 } // todo Get structure Id from str
            repository.register(
                login,
                email,
                password,
                groupNumber.await(),
                structureId.await()
            )
        }
        return if (tokensData.accessToken.isNotBlank() && tokensData.refreshToken.isNotBlank()) {
            localSecureStore.accessToken = tokensData.accessToken
            localSecureStore.refreshToken = tokensData.refreshToken
            ResponseState.Success(true)
        } else {
            ResponseState.Error(Throwable("Tokens mapping error"))
        }
    }
}