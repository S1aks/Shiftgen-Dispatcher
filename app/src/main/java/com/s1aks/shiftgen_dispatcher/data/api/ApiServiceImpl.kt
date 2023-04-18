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
import io.ktor.client.request.url
import io.ktor.http.HttpStatusCode
import io.ktor.util.InternalAPI

@OptIn(InternalAPI::class)
class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        client.post {
            url(LOGIN)
            body = loginRequest
        }.body()


    override suspend fun refresh(refreshRequest: RefreshRequest): LoginResponse =
        client.post {
            url(REFRESH)
            body = refreshRequest
        }.body()

    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse =
        client.post {
            url(REGISTER)
            body = registerRequest
        }.body()

    override suspend fun directions(): DirectionsResponse =
        client.get {
            url(DIRECTIONS)
        }.body()

    override suspend fun directionGet(idRequest: IdRequest): DirectionResponse =
        client.get {
            url(DIRECTION_GET)
        }.body()

    override suspend fun directionInsert(directionRequest: DirectionRequest): HttpStatusCode =
        client.post {
            url(DIRECTION_INSERT)
            body = directionRequest
        }.status

    override suspend fun directionUpdate(directionRequest: DirectionRequest): HttpStatusCode =
        client.post {
            url(DIRECTION_UPDATE)
            body = directionRequest
        }.status

    override suspend fun directionDelete(idRequest: IdRequest): HttpStatusCode =
        client.post {
            url(DIRECTION_DELETE)
            body = idRequest
        }.status

    override suspend fun shifts(shiftsRequest: ShiftsRequest): ShiftsResponse {
        TODO("Not yet implemented")
    }

    override suspend fun shiftGet(idRequest: IdRequest): ShiftResponse {
        TODO("Not yet implemented")
    }

    override suspend fun shiftInsert(shiftRequest: ShiftRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun shiftUpdate(shiftRequest: ShiftRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun shiftDelete(idRequest: IdRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun structures(): StructuresResponse {
        TODO("Not yet implemented")
    }

    override suspend fun structureGet(idRequest: IdRequest): StructureResponse {
        TODO("Not yet implemented")
    }

    override suspend fun structureInsert(structureRequest: StructureRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun structureUpdate(structureRequest: StructureRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun structureDelete(idRequest: IdRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun timeBlocks(): TimeBlocksResponse {
        TODO("Not yet implemented")
    }

    override suspend fun timeBlockGet(idRequest: IdRequest): TimeBlockResponse {
        TODO("Not yet implemented")
    }

    override suspend fun timeBlockInsert(timeBlockRequest: TimeBlockRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun timeBlockUpdate(timeBlockRequest: TimeBlockRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun timeBlockDelete(idRequest: IdRequest) {
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

    override suspend fun timeSheetInsert(timeSheetRequest: TimeSheetRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun timeSheetUpdate(timeSheetRequest: TimeSheetRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun timeSheetDelete(idRequest: IdRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun workers(): WorkersResponse {
        TODO("Not yet implemented")
    }

    override suspend fun workerGet(idRequest: IdRequest): WorkerResponse {
        TODO("Not yet implemented")
    }

    override suspend fun workerInsert(workerRequest: WorkerRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun workerUpdate(workerRequest: WorkerRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun workerDelete(idRequest: IdRequest) {
        TODO("Not yet implemented")
    }
}

//return try {
//    client.post<LoginResponse> {
//        url(LOGIN)
//        body = loginRequest
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