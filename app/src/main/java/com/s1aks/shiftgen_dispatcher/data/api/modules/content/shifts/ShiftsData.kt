package com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts

import com.s1aks.shiftgen_dispatcher.data.entities.Periodicity
import com.s1aks.shiftgen_dispatcher.data.entities.ShiftDTO
import com.s1aks.shiftgen_dispatcher.utils.LocalDateTimeSerializer
import com.s1aks.shiftgen_dispatcher.utils.YearMonthSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.YearMonth

@Serializable
data class ShiftRequest(
    val id: Int,
    val name: String,
    @Serializable(with = YearMonthSerializer::class)
    val periodYearMonth: YearMonth,
    val periodicity: Periodicity,
    val workerId: Int?,
    val structureId: Int,
    val directionId: Int,
    @Serializable(with = LocalDateTimeSerializer::class)
    val startTime: LocalDateTime,
    val timeBlocksIds: List<Int>
)

@Serializable
data class ShiftsRequest(
    @Serializable(with = YearMonthSerializer::class)
    val periodYearMonth: YearMonth
)

@Serializable
data class ShiftResponse(
    val shift: ShiftDTO
)

@Serializable
data class ShiftsResponse(
    val list: List<ShiftDTO>
)