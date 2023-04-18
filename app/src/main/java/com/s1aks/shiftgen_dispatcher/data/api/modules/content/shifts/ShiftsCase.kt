package com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts

import com.s1aks.shiftgen_dispatcher.data.api.ApiService.Companion.BASE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.IdRequest
import io.ktor.http.HttpStatusCode

interface ShiftsCase {
    suspend fun shifts(shiftsRequest: ShiftsRequest): ShiftsResponse
    suspend fun shiftGet(idRequest: IdRequest): ShiftResponse
    suspend fun shiftInsert(shiftRequest: ShiftRequest): HttpStatusCode
    suspend fun shiftUpdate(shiftRequest: ShiftRequest): HttpStatusCode
    suspend fun shiftDelete(idRequest: IdRequest): HttpStatusCode

    companion object {
        const val SHIFTS = "$BASE_URL/shifts"
        const val SHIFT_GET = "$BASE_URL/shift/get"
        const val SHIFT_INSERT = "$BASE_URL/shift/insert"
        const val SHIFT_UPDATE = "$BASE_URL/shift/update"
        const val SHIFT_DELETE = "$BASE_URL/shift/delete"
    }
}