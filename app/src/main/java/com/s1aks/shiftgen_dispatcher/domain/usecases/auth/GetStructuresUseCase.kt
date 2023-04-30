package com.s1aks.shiftgen_dispatcher.domain.usecases.auth

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.StructuresMap
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetStructuresUseCase(
    private val repository: Repository
) {
    suspend fun execute(): ResponseState<StructuresMap> {
        val structures = withContext(Dispatchers.IO) { repository.getStructures() }
        return ResponseState.Success(mapOf(0 to "Создать..").plus(structures))
    }
}