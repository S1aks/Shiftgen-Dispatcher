package com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDirectionUseCase(
    private val repository: Repository
) {
    suspend fun execute(id: Int): ResponseState<Direction> =
        ResponseState.Success(withContext(Dispatchers.IO) { repository.getDirection(id) })
}