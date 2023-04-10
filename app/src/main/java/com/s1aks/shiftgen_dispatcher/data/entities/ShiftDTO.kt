package com.s1aks.shiftgen_dispatcher.data.entities

import com.s1aks.shiftgen_dispatcher.utils.LocalDateTimeSerializer
import com.s1aks.shiftgen_dispatcher.utils.YearMonthSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.YearMonth

@Serializable
data class ShiftDTO(
    val id: Int = 0,
    val name: String,
    @Serializable(with = YearMonthSerializer::class)
    val periodYearMonth: YearMonth,
    val periodicity: Periodicity,
    var workerId: Int?,
    val structureId: Int,
    val directionId: Int,
    @Serializable(with = LocalDateTimeSerializer::class)
    val startTime: LocalDateTime,
    val timeBlocksIds: List<Int>
)
