package com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions

import com.s1aks.shiftgen_dispatcher.data.api.ApiService.Companion.BASE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.IdRequest
import io.ktor.http.HttpStatusCode

interface DirectionsCase {
    suspend fun directions(): DirectionsResponse
    suspend fun directionGet(idRequest: IdRequest): DirectionResponse
    suspend fun directionInsert(directionRequest: DirectionRequest): HttpStatusCode
    suspend fun directionUpdate(directionRequest: DirectionRequest): HttpStatusCode
    suspend fun directionDelete(idRequest: IdRequest): HttpStatusCode

    companion object {
        const val DIRECTIONS_URL = "$BASE_URL/directions"
        const val DIRECTION_GET_URL = "$BASE_URL/direction/get"
        const val DIRECTION_INSERT_URL = "$BASE_URL/direction/insert"
        const val DIRECTION_UPDATE_URL = "$BASE_URL/direction/update"
        const val DIRECTION_DELETE_URL = "$BASE_URL/direction/delete"
    }
}