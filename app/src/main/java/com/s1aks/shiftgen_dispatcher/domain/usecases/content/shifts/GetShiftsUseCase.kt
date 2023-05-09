package com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Shift
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.YearMonth

class GetShiftsUseCase(
    private val repository: Repository
) {
    suspend fun execute(yearMonth: YearMonth): ResponseState<List<Shift>> =
        ResponseState.Success(withContext(Dispatchers.IO) { repository.getShifts(yearMonth) })
}