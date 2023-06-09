package com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.Repository
import com.s1aks.shiftgen_dispatcher.domain.models.ShiftModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.YearMonth

class DeleteShiftUseCase(
    private val repository: Repository,
    private val getShiftsUseCase: GetShiftsUseCase
) {
    suspend fun execute(id: Int, yearMonth: YearMonth): ResponseState<List<ShiftModel>> {
        val deleteSuccess = withContext(Dispatchers.IO) { repository.deleteWorker(id) }
        if (deleteSuccess) {
            return getShiftsUseCase.execute(yearMonth)
        } else {
            throw RuntimeException("Ошибка удаления смены.")
        }
    }
}