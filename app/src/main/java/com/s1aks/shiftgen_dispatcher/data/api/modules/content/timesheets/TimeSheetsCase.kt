package com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets

import com.s1aks.shiftgen_dispatcher.data.api.ApiService.Companion.BASE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.IdRequest
import io.ktor.http.HttpStatusCode

interface TimeSheetsCase {
    suspend fun timeSheets(): TimeSheetsResponse
    suspend fun timeSheetsYearMonth(): TimeSheetsYearMonthRequest
    suspend fun timeSheetsByWorkerIdYearMonth(): TimeSheetsWorkerIdYearMonthRequest
    suspend fun timeSheetGet(idRequest: IdRequest): TimeSheetResponse
    suspend fun timeSheetInsert(timeSheetRequest: TimeSheetRequest): HttpStatusCode
    suspend fun timeSheetUpdate(timeSheetRequest: TimeSheetRequest): HttpStatusCode
    suspend fun timeSheetDelete(idRequest: IdRequest): HttpStatusCode

    companion object {
        const val TIMESHEETS = "$BASE_URL/timesheets"
        const val TIMESHEET_GET_BY_ID = "$BASE_URL/timesheet/get_by_id"
        const val TIMESHEET_GET_BY_WORKER_ID = "$BASE_URL/timesheet/get_by_worker_id"
        const val TIMESHEET_GET_BY_WORKER_ID_IN_YEAR_MONTH =
            "$BASE_URL/timesheet/get_by_worker_id_in_year_month"
        const val TIMESHEET_GET_BY_YEAR_MONTH = "$BASE_URL/timesheet/get_by_year_month"
        const val TIMESHEET_INSERT = "$BASE_URL/timesheet/insert"
        const val TIMESHEET_UPDATE = "$BASE_URL/timesheet/update"
        const val TIMESHEET_DELETE = "$BASE_URL/timesheet/delete"
    }
}