package com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Shift
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetShiftUseCase(
    private val repository: Repository
) {
    suspend fun execute(id: Int): ResponseState<Shift> =
        ResponseState.Success(withContext(Dispatchers.IO) { repository.getShift(id) })
}