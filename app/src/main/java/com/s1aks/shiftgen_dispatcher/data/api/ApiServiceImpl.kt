package com.s1aks.shiftgen_dispatcher.data.api

import com.s1aks.shiftgen_dispatcher.data.api.models.auth.*
import com.s1aks.shiftgen_dispatcher.data.api.models.auth.AuthCase.Companion.LOGIN
import com.s1aks.shiftgen_dispatcher.data.api.models.auth.AuthCase.Companion.REFRESH
import com.s1aks.shiftgen_dispatcher.data.api.models.auth.AuthCase.Companion.REGISTER
import com.s1aks.shiftgen_dispatcher.data.api.models.content.IdRequest
import com.s1aks.shiftgen_dispatcher.data.api.models.content.directions.DirectionRequest
import com.s1aks.shiftgen_dispatcher.data.api.models.content.directions.DirectionResponse
import com.s1aks.shiftgen_dispatcher.data.api.models.content.directions.DirectionsCase.Companion.DIRECTIONS
import com.s1aks.shiftgen_dispatcher.data.api.models.content.directions.DirectionsCase.Companion.DIRECTION_DELETE
import com.s1aks.shiftgen_dispatcher.data.api.models.content.directions.DirectionsCase.Companion.DIRECTION_GET
import com.s1aks.shiftgen_dispatcher.data.api.models.content.directions.DirectionsCase.Companion.DIRECTION_INSERT
import com.s1aks.shiftgen_dispatcher.data.api.models.content.directions.DirectionsCase.Companion.DIRECTION_UPDATE
import com.s1aks.shiftgen_dispatcher.data.api.models.content.directions.DirectionsResponse
import com.s1aks.shiftgen_dispatcher.data.api.models.content.shifts.ShiftRequest
import com.s1aks.shiftgen_dispatcher.data.api.models.content.shifts.ShiftResponse
import com.s1aks.shiftgen_dispatcher.data.api.models.content.shifts.ShiftsRequest
import com.s1aks.shiftgen_dispatcher.data.api.models.content.shifts.ShiftsResponse
import com.s1aks.shiftgen_dispatcher.data.api.models.content.structures.StructureRequest
import com.s1aks.shiftgen_dispatcher.data.api.models.content.structures.StructureResponse
import com.s1aks.shiftgen_dispatcher.data.api.models.content.structures.StructuresResponse
import com.s1aks.shiftgen_dispatcher.data.api.models.content.time_blocks.TimeBlockRequest
import com.s1aks.shiftgen_dispatcher.data.api.models.content.time_blocks.TimeBlockResponse
import com.s1aks.shiftgen_dispatcher.data.api.models.content.time_blocks.TimeBlocksResponse
import com.s1aks.shiftgen_dispatcher.data.api.models.content.timesheets.*
import com.s1aks.shiftgen_dispatcher.data.api.models.content.workers.WorkerRequest
import com.s1aks.shiftgen_dispatcher.data.api.models.content.workers.WorkerResponse
import com.s1aks.shiftgen_dispatcher.data.api.models.content.workers.WorkersResponse
import io.ktor.client.*
import io.ktor.client.request.*

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        client.post {
            url(LOGIN)
            body = loginRequest
        }


    override suspend fun refresh(refreshRequest: RefreshRequest): LoginResponse =
        client.post {
            url(REFRESH)
            body = refreshRequest
        }

    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse =
        client.post {
            url(REGISTER)
            body = registerRequest
        }

    override suspend fun directions(): DirectionsResponse =
        client.get {
            url(DIRECTIONS)
        }

    override suspend fun directionGet(idRequest: IdRequest): DirectionResponse =
        client.get {
            url(DIRECTION_GET)
        }

    override suspend fun directionInsert(directionRequest: DirectionRequest) {
        client.post<Unit> {
            url(DIRECTION_INSERT)
            body = directionRequest
        }
    }

    override suspend fun directionUpdate(directionRequest: DirectionRequest) {
        client.post<Unit> {
            url(DIRECTION_UPDATE)
            body = directionRequest
        }
    }

    override suspend fun directionDelete(idRequest: IdRequest) {
        client.post<Unit> {
            url(DIRECTION_DELETE)
            body = idRequest
        }
    }

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