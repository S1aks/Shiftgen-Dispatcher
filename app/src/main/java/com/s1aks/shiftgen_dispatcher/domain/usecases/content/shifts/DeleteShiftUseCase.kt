package com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteShiftUseCase(
    private val repository: Repository
) {
    suspend fun execute(id: Int): ResponseState<Boolean> =
        ResponseState.Success(withContext(Dispatchers.IO) { repository.deleteShift(id) })
}