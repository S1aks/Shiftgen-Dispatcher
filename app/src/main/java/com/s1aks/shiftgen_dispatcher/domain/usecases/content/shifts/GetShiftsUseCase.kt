package com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Action
import com.s1aks.shiftgen_dispatcher.domain.Repository
import com.s1aks.shiftgen_dispatcher.domain.models.ShiftModel
import com.s1aks.shiftgen_dispatcher.utils.fio
import com.s1aks.shiftgen_dispatcher.utils.toTimeString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class GetShiftsUseCase(
    private val repository: Repository
) {
    suspend fun execute(yearMonth: YearMonth): ResponseState<List<ShiftModel>> {
        val shifts = withContext(Dispatchers.IO) { repository.getShifts(yearMonth) }
        val workers = withContext(Dispatchers.IO) { repository.getWorkers() }
        val directions = withContext(Dispatchers.IO) { repository.getDirections() }
        val timeBlocks = withContext(Dispatchers.IO) { repository.getTimeBlocks() }
        val model = shifts.map { shift ->
            ShiftModel(
                shift.id,
                shift.startTime.format(DateTimeFormatter.ofPattern("dd.MM.y")),
                shift.startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                shift.name,
                directions.first { it.id == shift.directionId }.name,
                workers.firstOrNull { it.id == shift.workerId }.fio(),
                timeBlocks.filter { it.action == Action.WORK }.sumOf { it.duration }
                    .toTimeString()
            )
        }
        return ResponseState.Success(model)
    }
}