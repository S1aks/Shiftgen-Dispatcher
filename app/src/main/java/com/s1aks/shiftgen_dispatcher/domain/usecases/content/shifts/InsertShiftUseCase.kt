package com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Shift
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertShiftUseCase(
    private val repository: Repository
) {
    suspend fun execute(shift: Shift): ResponseState<Boolean> =
        ResponseState.Success(withContext(Dispatchers.IO) { repository.insertShift(shift) })
}