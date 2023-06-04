package com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertDirectionUseCase(
    private val repository: Repository
) {
    suspend fun execute(direction: Direction): ResponseState<Boolean> =
        ResponseState.Success(withContext(Dispatchers.IO) { repository.insertDirection(direction) })
}