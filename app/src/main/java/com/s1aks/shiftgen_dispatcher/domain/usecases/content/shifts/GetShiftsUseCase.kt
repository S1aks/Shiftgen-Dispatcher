package com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Action
import com.s1aks.shiftgen_dispatcher.domain.Repository
import com.s1aks.shiftgen_dispatcher.domain.models.ShiftModel
import com.s1aks.shiftgen_dispatcher.utils.fio
import com.s1aks.shiftgen_dispatcher.utils.toTimeString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancelChildren
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class GetShiftsUseCase(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val repository: Repository
) {
    suspend fun execute(yearMonth: YearMonth): ResponseState<List<ShiftModel>> {
        scope.coroutineContext.cancelChildren()
        val shifts = scope.async { repository.getShifts(yearMonth) }
        val workers = scope.async { repository.getWorkers() }
        val directions = scope.async { repository.getDirections() }
        val timeBlocks = scope.async { repository.getTimeBlocks() }
        awaitAll(shifts, workers, directions, timeBlocks)
        val model = shifts.await().map { shift ->
            ShiftModel(
                shift.startTime.format(DateTimeFormatter.ofPattern("dd.MM.y")),
                shift.startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                shift.name,
                directions.await().first { it.id == shift.directionId }.name,
                workers.await().firstOrNull { it.id == shift.workerId }.fio(),
                timeBlocks.await().filter { it.action == Action.WORK }.sumOf { it.duration }
                    .toTimeString()
            )
        }
        return ResponseState.Success(model)
    }
}