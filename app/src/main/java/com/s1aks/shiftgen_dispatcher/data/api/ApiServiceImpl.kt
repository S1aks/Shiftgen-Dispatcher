package com.s1aks.shiftgen_dispatcher.data.api

import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.AuthCase.Companion.LOGIN
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.AuthCase.Companion.REFRESH
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.AuthCase.Companion.REGISTER
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.LoginRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.LoginResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.RefreshRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.RegisterRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.RegisterResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.IdRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsCase.Companion.DIRECTIONS
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsCase.Companion.DIRECTION_DELETE
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsCase.Companion.DIRECTION_GET
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsCase.Companion.DIRECTION_INSERT
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsCase.Companion.DIRECTION_UPDATE
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
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        client.post(LOGIN) {
            setBody(loginRequest)
        }.body()


    override suspend fun refresh(refreshRequest: RefreshRequest): LoginResponse =
        client.post(REFRESH) {
            setBody(refreshRequest)
        }.body()

    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse =
        client.post(REGISTER) {
            setBody(registerRequest)
        }.body()

    override suspend fun directions(): DirectionsResponse =
        client.get(DIRECTIONS) {
        }.body()

    override suspend fun directionGet(idRequest: IdRequest): DirectionResponse =
        client.get(DIRECTION_GET) {
        }.body()

    override suspend fun directionInsert(directionRequest: DirectionRequest): HttpStatusCode =
        client.post(DIRECTION_INSERT) {
            setBody(directionRequest)
        }.status

    override suspend fun directionUpdate(directionRequest: DirectionRequest): HttpStatusCode =
        client.post(DIRECTION_UPDATE) {
            setBody(directionRequest)
        }.status

    override suspend fun directionDelete(idRequest: IdRequest): HttpStatusCode =
        client.post(DIRECTION_DELETE) {
            setBody(idRequest)
        }.status

    override suspend fun shifts(shiftsRequest: ShiftsRequest): ShiftsResponse {
        TODO("Not yet implemented")
    }

    override suspend fun shiftGet(idRequest: IdRequest): ShiftResponse {
        TODO("Not yet implemented")
    }

    override suspend fun shiftInsert(shiftRequest: ShiftRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun shiftUpdate(shiftRequest: ShiftRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun shiftDelete(idRequest: IdRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun structures(): StructuresResponse {
        TODO("Not yet implemented")
    }

    override suspend fun structureGet(idRequest: IdRequest): StructureResponse {
        TODO("Not yet implemented")
    }

    override suspend fun structureInsert(structureRequest: StructureRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun structureUpdate(structureRequest: StructureRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun structureDelete(idRequest: IdRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun timeBlocks(): TimeBlocksResponse {
        TODO("Not yet implemented")
    }

    override suspend fun timeBlockGet(idRequest: IdRequest): TimeBlockResponse {
        TODO("Not yet implemented")
    }

    override suspend fun timeBlockInsert(timeBlockRequest: TimeBlockRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun timeBlockUpdate(timeBlockRequest: TimeBlockRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun timeBlockDelete(idRequest: IdRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun timeSheets(): TimeSheetsResponse {
        TODO("Not yet implemented")
    }

    override suspend fun timeSheetsYearMonth(): TimeSheetsYearMonthRequest {
        TODO("Not yet implemented")
    }

    override suspend fun timeSheetsByWorkerIdYearMonth(): TimeSheetsWorkerIdYearMonthRequest {
        TODO("Not yet implemented")
    }

    override suspend fun timeSheetGet(idRequest: IdRequest): TimeSheetResponse {
        TODO("Not yet implemented")
    }

    override suspend fun timeSheetInsert(timeSheetRequest: TimeSheetRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun timeSheetUpdate(timeSheetRequest: TimeSheetRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun timeSheetDelete(idRequest: IdRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun workers(): WorkersResponse {
        TODO("Not yet implemented")
    }

    override suspend fun workerGet(idRequest: IdRequest): WorkerResponse {
        TODO("Not yet implemented")
    }

    override suspend fun workerInsert(workerRequest: WorkerRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun workerUpdate(workerRequest: WorkerRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun workerDelete(idRequest: IdRequest): HttpStatusCode {
        TODO("Not yet implemented")
    }
}

//return try {
//    client.post<LoginResponse> {
//        url(LOGIN)
//        setBody(loginRequest
//    }
//} catch (ex: RedirectResponseException) {
//    // 3xx - responses
//    println("Error: ${ex.response.status.description}")
//    null
//} catch (ex: ClientRequestException) {
//    // 4xx - responses
//    println("Error: ${ex.response.status.description}")
//    null
//} catch (ex: ServerResponseException) {
//    // 5xx - response
//    println("Error: ${ex.response.status.description}")
//    null
//}