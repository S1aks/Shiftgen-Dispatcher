package com.s1aks.shiftgen_dispatcher.domain.usecases.content.structures

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Structure
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertStructureUseCase(
    private val repository: Repository
) {
    suspend fun execute(structure: Structure): ResponseState<Boolean> =
        ResponseState.Success(withContext(Dispatchers.IO) { repository.insertStructure(structure) })
}