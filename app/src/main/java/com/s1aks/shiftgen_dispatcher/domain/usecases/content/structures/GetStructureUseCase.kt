package com.s1aks.shiftgen_dispatcher.domain.usecases.content.structures

import android.util.Log
import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Structure
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetStructureUseCase(
    private val repository: Repository,
    private val localSecureStore: LocalSecureStore
) {
    suspend fun execute(): ResponseState<Structure> {
        val structureId = localSecureStore.structureId
        Log.d("TAG", structureId.toString())
        if (structureId != null) {
            val structure =
                withContext(Dispatchers.IO) { repository.getStructure(structureId) }
            return ResponseState.Success(structure)
        } else {
            throw RuntimeException("Ошибка id структуры.")
        }
    }
}