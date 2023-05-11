package com.s1aks.shiftgen_dispatcher.data.api

import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.AccessRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.AuthCase.Companion.ACCESS_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.AuthCase.Companion.LOGIN_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.AuthCase.Companion.REFRESH_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.AuthCase.Companion.REGISTER_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.LoginRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.LoginResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.RefreshRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.RegisterRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.RegisterResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.IdRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsCase.Companion.DIRECTIONS_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsCase.Companion.DIRECTION_DELETE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsCase.Companion.DIRECTION_GET_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsCase.Companion.DIRECTION_INSERT_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsCase.Companion.DIRECTION_UPDATE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftsCase.Companion.SHIFTS_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftsCase.Companion.SHIFT_DELETE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftsCase.Companion.SHIFT_GET_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftsCase.Companion.SHIFT_INSERT_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftsCase.Companion.SHIFT_UPDATE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftsRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftsResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructureRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructureResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructuresCase.Companion.STRUCTURES_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructuresCase.Companion.STRUCTURE_DELETE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructuresCase.Companion.STRUCTURE_GET_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructuresCase.Companion.STRUCTURE_INSERT_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructuresCase.Companion.STRUCTURE_UPDATE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructuresResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks.TimeBlockRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks.TimeBlockResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks.TimeBlocksCase.Companion.TIME_BLOCKS_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks.TimeBlocksCase.Companion.TIME_BLOCK_DELETE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks.TimeBlocksCase.Companion.TIME_BLOCK_GET_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks.TimeBlocksCase.Companion.TIME_BLOCK_INSERT_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks.TimeBlocksCase.Companion.TIME_BLOCK_UPDATE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks.TimeBlocksResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsCase.Companion.TIMESHEETS_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsCase.Companion.TIMESHEET_DELETE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsCase.Companion.TIMESHEET_GET_BY_ID_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsCase.Companion.TIMESHEET_GET_BY_WORKER_ID_IN_YEAR_MONTH_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsCase.Companion.TIMESHEET_GET_BY_YEAR_MONTH_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsCase.Companion.TIMESHEET_INSERT_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsCase.Companion.TIMESHEET_UPDATE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsWorkerIdYearMonthRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsYearMonthRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers.WorkerRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers.WorkerResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers.WorkersCase.Companion.WORKERS_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers.WorkersCase.Companion.WORKER_GET_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers.WorkersCase.Companion.WORKER_INSERT_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers.WorkersCase.Companion.WORKER_UPDATE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers.WorkersResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

    private suspend inline fun <reified T> HttpResponse.getData(): T {
        return if (status.isSuccess()) {
            body()
        } else {
            throw RuntimeException(bodyAsText())
        }
    }

    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        client.post(LOGIN_URL) { setBody(loginRequest) }.getData()

    override suspend fun access(accessRequest: AccessRequest): HttpStatusCode =
        client.post(ACCESS_URL) { setBody(accessRequest) }.status

    override suspend fun refresh(refreshRequest: RefreshRequest): LoginResponse =
        client.post(REFRESH_URL) { setBody(refreshRequest) }.getData()

    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse =
        client.post(REGISTER_URL) { setBody(registerRequest) }.getData()

    override suspend fun directions(): DirectionsResponse =
        client.get(DIRECTIONS_URL).getData()

    override suspend fun directionGet(idRequest: IdRequest): DirectionResponse =
        client.get(DIRECTION_GET_URL) { setBody(idRequest) }.getData()

    override suspend fun directionInsert(directionRequest: DirectionRequest): HttpStatusCode =
        client.post(DIRECTION_INSERT_URL) { setBody(directionRequest) }.status

    override suspend fun directionUpdate(directionRequest: DirectionRequest): HttpStatusCode =
        client.post(DIRECTION_UPDATE_URL) { setBody(directionRequest) }.status

    override suspend fun directionDelete(idRequest: IdRequest): HttpStatusCode =
        client.post(DIRECTION_DELETE_URL) { setBody(idRequest) }.status

    override suspend fun shifts(shiftsRequest: ShiftsRequest): ShiftsResponse =
        client.get(SHIFTS_URL).getData()

    override suspend fun shiftGet(idRequest: IdRequest): ShiftResponse =
        client.get(SHIFT_GET_URL) { setBody(idRequest) }.getData()

    override suspend fun shiftInsert(shiftRequest: ShiftRequest): HttpStatusCode =
        client.post(SHIFT_INSERT_URL) { setBody(shiftRequest) }.status

    override suspend fun shiftUpdate(shiftRequest: ShiftRequest): HttpStatusCode =
        client.post(SHIFT_UPDATE_URL) { setBody(shiftRequest) }.status

    override suspend fun shiftDelete(idRequest: IdRequest): HttpStatusCode =
        client.post(SHIFT_DELETE_URL) { setBody(idRequest) }.status

    override suspend fun structures(): StructuresResponse =
        client.get(STRUCTURES_URL).getData()

    override suspend fun structureGet(idRequest: IdRequest): StructureResponse =
        client.get(STRUCTURE_GET_URL) { setBody(idRequest) }.getData()

    override suspend fun structureInsert(structureRequest: StructureRequest): HttpStatusCode =
        client.post(STRUCTURE_INSERT_URL) { setBody(structureRequest) }.status

    override suspend fun structureUpdate(structureRequest: StructureRequest): HttpStatusCode =
        client.post(STRUCTURE_UPDATE_URL) { setBody(structureRequest) }.status

    override suspend fun structureDelete(idRequest: IdRequest): HttpStatusCode =
        client.post(STRUCTURE_DELETE_URL) { setBody(idRequest) }.status

    override suspend fun timeBlocks(): TimeBlocksResponse =
        client.get(TIME_BLOCKS_URL).getData()

    override suspend fun timeBlockGet(idRequest: IdRequest): TimeBlockResponse =
        client.get(TIME_BLOCK_GET_URL) { setBody(idRequest) }.getData()

    override suspend fun timeBlockInsert(timeBlockRequest: TimeBlockRequest): HttpStatusCode =
        client.post(TIME_BLOCK_INSERT_URL) { setBody(timeBlockRequest) }.status

    override suspend fun timeBlockUpdate(timeBlockRequest: TimeBlockRequest): HttpStatusCode =
        client.post(TIME_BLOCK_UPDATE_URL) { setBody(timeBlockRequest) }.status

    override suspend fun timeBlockDelete(idRequest: IdRequest): HttpStatusCode =
        client.post(TIME_BLOCK_DELETE_URL) { setBody(idRequest) }.status

    override suspend fun timeSheets(): TimeSheetsResponse =
        client.get(TIMESHEETS_URL).getData()

    override suspend fun timeSheetGet(idRequest: IdRequest): TimeSheetResponse =
        client.get(TIMESHEET_GET_BY_ID_URL) { setBody(idRequest) }.getData()

    override suspend fun timeSheetsGetByYearMonth(
        timeSheetsYearMonthRequest: TimeSheetsYearMonthRequest
    ): TimeSheetsResponse =
        client.get(TIMESHEET_GET_BY_YEAR_MONTH_URL) { setBody(timeSheetsYearMonthRequest) }
            .getData()

    override suspend fun timeSheetsGetByWorkerIdYearMonth(
        timeSheetsWorkerIdYearMonthRequest: TimeSheetsWorkerIdYearMonthRequest
    ): TimeSheetsResponse =
        client.get(TIMESHEET_GET_BY_WORKER_ID_IN_YEAR_MONTH_URL) {
            setBody(timeSheetsWorkerIdYearMonthRequest)
        }.getData()

    override suspend fun timeSheetInsert(timeSheetRequest: TimeSheetRequest): HttpStatusCode =
        client.post(TIMESHEET_INSERT_URL) { setBody(timeSheetRequest) }.status

    override suspend fun timeSheetUpdate(timeSheetRequest: TimeSheetRequest): HttpStatusCode =
        client.post(TIMESHEET_UPDATE_URL) { setBody(timeSheetRequest) }.status

    override suspend fun timeSheetDelete(idRequest: IdRequest): HttpStatusCode =
        client.post(TIMESHEET_DELETE_URL) { setBody(idRequest) }.status

    override suspend fun workers(): WorkersResponse =
        client.get(WORKERS_URL).getData()

    override suspend fun workerGet(idRequest: IdRequest): WorkerResponse =
        client.get(WORKER_GET_URL) { setBody(idRequest) }.getData()

    override suspend fun workerInsert(workerRequest: WorkerRequest): HttpStatusCode =
        client.post(WORKER_INSERT_URL) { setBody(workerRequest) }.status

    override suspend fun workerUpdate(workerRequest: WorkerRequest): HttpStatusCode =
        client.post(WORKER_UPDATE_URL) { setBody(workerRequest) }.status

    override suspend fun workerDelete(idRequest: IdRequest): HttpStatusCode =
        client.post(WORKER_INSERT_URL) { setBody(idRequest) }.status
}
