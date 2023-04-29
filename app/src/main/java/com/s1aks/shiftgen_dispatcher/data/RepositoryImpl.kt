package com.s1aks.shiftgen_dispatcher.data

import com.s1aks.shiftgen_dispatcher.data.api.ApiService
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.IdRequest
import com.s1aks.shiftgen_dispatcher.data.entities.Groups
import com.s1aks.shiftgen_dispatcher.domain.Repository
import com.s1aks.shiftgen_dispatcher.domain.models.Direction
import com.s1aks.shiftgen_dispatcher.domain.models.LoginData
import com.s1aks.shiftgen_dispatcher.domain.models.RegisterData
import com.s1aks.shiftgen_dispatcher.domain.models.Shift
import com.s1aks.shiftgen_dispatcher.domain.models.Structure
import com.s1aks.shiftgen_dispatcher.domain.models.StructureMap
import com.s1aks.shiftgen_dispatcher.domain.models.TimeBlock
import com.s1aks.shiftgen_dispatcher.domain.models.TimeSheet
import com.s1aks.shiftgen_dispatcher.domain.models.TokensData
import com.s1aks.shiftgen_dispatcher.domain.models.Worker
import io.ktor.http.isSuccess
import java.time.YearMonth

class RepositoryImpl(
    private val apiService: ApiService
) : Repository {
    override suspend fun login(loginData: LoginData): TokensData =
        apiService.login(loginData.toLoginRequest()).toTokensData()

    override suspend fun register(registerData: RegisterData): TokensData {
        val structures = getStructures()  // todo На сервере создать метод взятия структуры по имени
        return apiService.register(
            registerData.toRegisterRequest(
                { groupName ->
                    Groups.values().find { it.groupName == groupName }?.ordinal
                        ?: throw NoSuchElementException("Error! Group not found.")
                },
                { structureName ->
                    structures.filterValues { it == structureName }.keys.first()
                }
            )
        ).toTokensData()
    }

    override suspend fun getDirections(): List<Direction> =
        apiService.directions().toDirectionsList()

    override suspend fun getDirection(id: Int): Direction =
        apiService.directionGet(IdRequest(id)).toDirection()

    override suspend fun insertDirection(direction: Direction): Boolean =
        apiService.directionInsert(direction.toDirectionRequest()).isSuccess()

    override suspend fun updateDirection(direction: Direction): Boolean =
        apiService.directionUpdate(direction.toDirectionRequest()).isSuccess()

    override suspend fun deleteDirection(id: Int): Boolean =
        apiService.directionDelete(IdRequest(id)).isSuccess()

    override suspend fun getShifts(yearMonth: YearMonth): List<Shift> =
        apiService.shifts(yearMonth.toShiftsRequest()).toShiftsList()

    override suspend fun getShift(id: Int): Shift =
        apiService.shiftGet(IdRequest(id)).toShift()

    override suspend fun insertShift(shift: Shift): Boolean =
        apiService.shiftInsert(shift.toShiftRequest()).isSuccess()

    override suspend fun updateShift(shift: Shift): Boolean =
        apiService.shiftUpdate(shift.toShiftRequest()).isSuccess()

    override suspend fun deleteShift(id: Int): Boolean =
        apiService.shiftDelete(IdRequest(id)).isSuccess()

    override suspend fun getStructures(): StructureMap =
        apiService.structures().toStructureMap()

    override suspend fun getStructure(id: Int): Structure =
        apiService.structureGet(IdRequest(id)).toStructure()

    override suspend fun insertStructure(structure: Structure): Boolean =
        apiService.structureInsert(structure.toStructureRequest()).isSuccess()

    override suspend fun updateStructure(structure: Structure): Boolean =
        apiService.structureUpdate(structure.toStructureRequest()).isSuccess()

    override suspend fun deleteStructure(id: Int): Boolean =
        apiService.structureDelete(IdRequest(id)).isSuccess()

    override suspend fun getTimeBlocks(): List<TimeBlock> =
        apiService.timeBlocks().toTimeBlocksList()

    override suspend fun getTimeBlock(id: Int): TimeBlock =
        apiService.timeBlockGet(IdRequest(id)).toTimeBlock()

    override suspend fun insertTimeBlock(timeBlock: TimeBlock): Boolean =
        apiService.timeBlockInsert(timeBlock.toTimeBlockRequest()).isSuccess()

    override suspend fun updateTimeBlock(timeBlock: TimeBlock): Boolean =
        apiService.timeBlockUpdate(timeBlock.toTimeBlockRequest()).isSuccess()

    override suspend fun deleteTimeBlock(id: Int): Boolean =
        apiService.timeBlockDelete(IdRequest(id)).isSuccess()

    override suspend fun getTimeSheet(): List<TimeSheet> =
        apiService.timeSheets().toTimeSheetsList()

    override suspend fun getTimeSheet(id: Int): TimeSheet =
        apiService.timeSheetGet(IdRequest(id)).toTimeSheet()

    override suspend fun getTimeSheetByYearMonth(yearMonth: YearMonth): List<TimeSheet> =
        apiService.timeSheetsGetByYearMonth(yearMonth.toTimeSheetsYearMonthRequest())
            .toTimeSheetsList()

    override suspend fun getTimeSheetByWorkerIdYearMonth(
        id: Int, yearMonth: YearMonth
    ): List<TimeSheet> =
        apiService.timeSheetsGetByWorkerIdYearMonth(
            yearMonth.toTimeSheetsYearMonthRequestWithWorkerId(id)
        ).toTimeSheetsList()

    override suspend fun insertTimeSheet(timeSheet: TimeSheet): Boolean =
        apiService.timeSheetInsert(timeSheet.toTimeSheetRequest()).isSuccess()

    override suspend fun updateTimeSheet(timeSheet: TimeSheet): Boolean =
        apiService.timeSheetUpdate(timeSheet.toTimeSheetRequest()).isSuccess()

    override suspend fun deleteTimeSheet(id: Int): Boolean =
        apiService.timeSheetDelete(IdRequest(id)).isSuccess()

    override suspend fun getWorkers(): List<Worker> =
        apiService.workers().toWorkersList()

    override suspend fun getWorker(id: Int): Worker =
        apiService.workerGet(IdRequest(id)).toWorker()

    override suspend fun insertWorker(worker: Worker): Boolean =
        apiService.workerInsert(worker.toWorkerRequest()).isSuccess()

    override suspend fun updateWorker(worker: Worker): Boolean =
        apiService.workerUpdate(worker.toWorkerRequest()).isSuccess()

    override suspend fun deleteWorker(id: Int): Boolean =
        apiService.workerDelete(IdRequest(id)).isSuccess()
}


