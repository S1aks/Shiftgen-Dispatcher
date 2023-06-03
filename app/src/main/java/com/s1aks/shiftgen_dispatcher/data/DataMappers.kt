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
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructureIdResponse
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
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.data.entities.Groups
import com.s1aks.shiftgen_dispatcher.data.entities.LoginData
import com.s1aks.shiftgen_dispatcher.data.entities.RegisterData
import com.s1aks.shiftgen_dispatcher.data.entities.Shift
import com.s1aks.shiftgen_dispatcher.data.entities.Structure
import com.s1aks.shiftgen_dispatcher.data.entities.StructuresMap
import com.s1aks.shiftgen_dispatcher.data.entities.TimeBlock
import com.s1aks.shiftgen_dispatcher.data.entities.TimeSheet
import com.s1aks.shiftgen_dispatcher.data.entities.TokensData
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import java.time.YearMonth

internal fun String.toGroupNumber(): Int = Groups.values().find { it.groupName == this }?.ordinal
    ?: throw NullPointerException("Группа не существует.")

internal fun LoginData.toLoginRequest(): LoginRequest = LoginRequest(login, password)

internal fun LoginResponse.toTokensData(): TokensData = TokensData(accessToken, refreshToken)

internal fun RegisterData.toRegisterRequest(
    structureId: Int
): RegisterRequest =
    RegisterRequest(login, email, password, group.toGroupNumber(), structureId, dispatcherPin)

internal fun RegisterResponse.toTokensData(): TokensData = TokensData(accessToken, refreshToken)

internal fun Direction.toDirectionRequest(): DirectionRequest = DirectionRequest(id, name)

internal fun DirectionResponse.toDirection(): Direction = direction

internal fun DirectionsResponse.toDirectionsList(): List<Direction> = list

internal fun YearMonth.toShiftsRequest(): ShiftsRequest = ShiftsRequest(this)

internal fun Shift.toShiftRequest(): ShiftRequest = ShiftRequest(
    id, name, periodYearMonth, periodicity, workerId,
    structureId, directionId, startTime, timeBlocksIds
)

internal fun ShiftResponse.toShift(): Shift = shift

internal fun ShiftsResponse.toShiftsList(): List<Shift> = list

internal fun Structure.toStructureRequest(): StructureRequest = StructureRequest(
    id, name, description, restHours, allowedConsecutiveNights, nightStartHour, nightEndHour
)

internal fun StructureResponse.toStructure(): Structure = structure

internal fun StructureIdResponse.toStructureId(): Int = structureId

internal fun StructuresResponse.toStructureMap(): StructuresMap = list

internal fun TimeBlockResponse.toTimeBlock(): TimeBlock = timeBlock

internal fun TimeBlocksResponse.toTimeBlocksList(): List<TimeBlock> = list

internal fun TimeBlock.toTimeBlockRequest(): TimeBlockRequest =
    TimeBlockRequest(id, structureId, name, duration, action)

internal fun TimeSheetResponse.toTimeSheet(): TimeSheet = timesheet

internal fun TimeSheetsResponse.toTimeSheetsList(): List<TimeSheet> = list

internal fun YearMonth.toTimeSheetsYearMonthRequest(): TimeSheetsYearMonthRequest =
    TimeSheetsYearMonthRequest(this)

internal fun YearMonth.toTimeSheetsYearMonthRequestWithWorkerId(id: Int): TimeSheetsWorkerIdYearMonthRequest =
    TimeSheetsWorkerIdYearMonthRequest(id, this)

internal fun TimeSheet.toTimeSheetRequest(): TimeSheetRequest = TimeSheetRequest(
    id, workerId, structureId, periodYearMonth, workedTime, calculatedTime, correctionTime
)

internal fun WorkerResponse.toWorker(): Worker = worker

internal fun WorkersResponse.toWorkersList(): List<Worker> = list

internal fun Worker.toWorkerRequest(): WorkerRequest = WorkerRequest(
    id, personnelNumber, userId, structureId, firstName, lastName, patronymic, accessToDirections
)