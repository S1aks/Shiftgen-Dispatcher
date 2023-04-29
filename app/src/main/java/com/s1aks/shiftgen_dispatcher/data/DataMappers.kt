package com.s1aks.shiftgen_dispatcher.data

import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.LoginRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.LoginResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.RegisterRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.RegisterResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftsRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftsResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructureRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructureResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructuresResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks.TimeBlockRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks.TimeBlockResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks.TimeBlocksResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsWorkerIdYearMonthRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsYearMonthRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers.WorkerRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers.WorkerResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers.WorkersResponse
import com.s1aks.shiftgen_dispatcher.data.entities.DirectionDTO
import com.s1aks.shiftgen_dispatcher.data.entities.ShiftDTO
import com.s1aks.shiftgen_dispatcher.data.entities.StructureDTO
import com.s1aks.shiftgen_dispatcher.data.entities.TimeBlockDTO
import com.s1aks.shiftgen_dispatcher.data.entities.TimeSheetDTO
import com.s1aks.shiftgen_dispatcher.data.entities.WorkerDTO
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
import java.time.YearMonth

internal fun LoginData.toLoginRequest(): LoginRequest = LoginRequest(login, password)

internal fun LoginResponse.toTokensData(): TokensData = TokensData(accessToken, refreshToken)

internal fun RegisterData.toRegisterRequest(
    groupInsertBlock: (String) -> Int, structureInsertBlock: (String) -> Int
): RegisterRequest = RegisterRequest(
    login, email, password, groupInsertBlock(group), structureInsertBlock(structure)
)

internal fun RegisterResponse.toTokensData(): TokensData = TokensData(accessToken, refreshToken)

internal fun Direction.toDirectionRequest(): DirectionRequest = DirectionRequest(id, name)

internal fun DirectionDTO.toDirection(): Direction = Direction(id, name, structureId)

internal fun DirectionResponse.toDirection(): Direction = direction.toDirection()

internal fun DirectionsResponse.toDirectionsList(): List<Direction> = list.map { it.toDirection() }

internal fun YearMonth.toShiftsRequest(): ShiftsRequest = ShiftsRequest(this)

internal fun Shift.toShiftRequest(): ShiftRequest = ShiftRequest(
    id, name, periodYearMonth, periodicity, workerId,
    structureId, directionId, startTime, timeBlocksIds
)

internal fun ShiftDTO.toShift(): Shift = Shift(
    id, name, periodYearMonth, periodicity, workerId,
    structureId, directionId, startTime, timeBlocksIds
)

internal fun ShiftResponse.toShift(): Shift = shift.toShift()

internal fun ShiftsResponse.toShiftsList(): List<Shift> = list.map { it.toShift() }

internal fun Structure.toStructureRequest(): StructureRequest = StructureRequest(
    id, name, description, restHours, allowedConsecutiveNights, nightStartHour, nightEndHour
)

internal fun StructureDTO.toStructure(): Structure = Structure(
    id, name, description, restHours, allowedConsecutiveNights, nightStartHour, nightEndHour
)

internal fun StructureResponse.toStructure(): Structure = structure.toStructure()

internal fun StructuresResponse.toStructureMap(): StructureMap = list

internal fun TimeBlockDTO.toTimeBlock(): TimeBlock =
    TimeBlock(id, structureId, name, duration, action)

internal fun TimeBlockResponse.toTimeBlock(): TimeBlock = timeBlock.toTimeBlock()

internal fun TimeBlocksResponse.toTimeBlocksList(): List<TimeBlock> = list.map { it.toTimeBlock() }

internal fun TimeBlock.toTimeBlockRequest(): TimeBlockRequest =
    TimeBlockRequest(id, structureId, name, duration, action)

internal fun TimeSheetDTO.toTimeSheet(): TimeSheet = TimeSheet(
    id, workerId, structureId, periodYearMonth, workedTime, calculatedTime, correctionTime
)

internal fun TimeSheetResponse.toTimeSheet(): TimeSheet = timesheet.toTimeSheet()

internal fun TimeSheetsResponse.toTimeSheetsList(): List<TimeSheet> = list.map { it.toTimeSheet() }

internal fun YearMonth.toTimeSheetsYearMonthRequest(): TimeSheetsYearMonthRequest =
    TimeSheetsYearMonthRequest(this)

internal fun YearMonth.toTimeSheetsYearMonthRequestWithWorkerId(id: Int): TimeSheetsWorkerIdYearMonthRequest =
    TimeSheetsWorkerIdYearMonthRequest(id, this)

internal fun TimeSheet.toTimeSheetRequest(): TimeSheetRequest = TimeSheetRequest(
    id, workerId, structureId, periodYearMonth, workedTime, calculatedTime, correctionTime
)

internal fun WorkerDTO.toWorker(): Worker = Worker(
    id, personnelNumber, userId, structureId, firstName, lastName, patronymic, accessToDirections
)

internal fun WorkerResponse.toWorker(): Worker = worker.toWorker()

internal fun WorkersResponse.toWorkersList(): List<Worker> = list.map { it.toWorker() }

internal fun Worker.toWorkerRequest(): WorkerRequest = WorkerRequest(
    id, personnelNumber, userId, structureId, firstName, lastName, patronymic, accessToDirections
)