package com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteDirectionUseCase(
    private val repository: Repository
) {
    suspend fun execute(id: Int): ResponseState<List<Direction>> {
        val deleteSuccess = withContext(Dispatchers.IO) { repository.deleteDirection(id) }
        if (deleteSuccess) {
            val directions = withContext(Dispatchers.IO) { repository.getDirections() }
            return ResponseState.Success(directions)
        } else {
            throw RuntimeException("Ошибка удаления направления.")
        }
    }
}