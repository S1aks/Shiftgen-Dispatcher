package com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.Repository
import com.s1aks.shiftgen_dispatcher.domain.models.ShiftItemModel
import com.s1aks.shiftgen_dispatcher.utils.fio
import com.s1aks.shiftgen_dispatcher.utils.toDayString
import com.s1aks.shiftgen_dispatcher.utils.toTimeString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.YearMonth

class GetShiftsUseCase(
    private val repository: Repository
) {
    suspend fun execute(yearMonth: YearMonth): ResponseState<List<ShiftItemModel>> {
        val shifts = withContext(Dispatchers.IO) { repository.getShifts(yearMonth) }
        val workers = withContext(Dispatchers.IO) { repository.getWorkers() }
        val directions = withContext(Dispatchers.IO) { repository.getDirections() }
        val shiftItemModelList = shifts.map { shift ->
            val direction = directions.first { it.id == shift.directionId }.name
            val worker = shift.workerId?.let { workerId ->
                workers.firstOrNull { it.id == workerId }
            }.fio() ?: ""
            ShiftItemModel(
                shift.id,
                shift.name,
                worker,
                direction,
                shift.startTime.toDayString(),
                shift.startTime.toTimeString(),
                (shift.duration - shift.restDuration).toTimeString()
            )
        }
        return ResponseState.Success(shiftItemModelList)
    }
}