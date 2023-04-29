package com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets

import com.s1aks.shiftgen_dispatcher.data.entities.TimeSheetDTO
import com.s1aks.shiftgen_dispatcher.utils.YearMonthSerializer
import kotlinx.serialization.Serializable
import java.time.YearMonth

@Serializable
data class TimeSheetRequest(
    val id: Int,
    val workerId: Int,
    val structureId: Int,
    @Serializable(with = YearMonthSerializer::class)
    val periodYearMonth: YearMonth,
    val workedTime: Long,
    val calculatedTime: Long,
    val correctionTime: Long
)

@Serializable
data class TimeSheetsWorkerIdYearMonthRequest(
    val workerId: Int,
    @Serializable(with = YearMonthSerializer::class)
    val yearMonth: YearMonth
)

@Serializable
data class TimeSheetsYearMonthRequest(
    @Serializable(with = YearMonthSerializer::class)
    val yearMonth: YearMonth
)

@Serializable
data class TimeSheetResponse(
    val timesheet: TimeSheetDTO
)

@Serializable
data class TimeSheetsResponse(
    val list: List<TimeSheetDTO>
)