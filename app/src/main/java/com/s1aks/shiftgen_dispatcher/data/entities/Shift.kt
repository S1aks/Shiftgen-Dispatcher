package com.s1aks.shiftgen_dispatcher.data.entities

import com.s1aks.shiftgen_dispatcher.utils.LocalDateTimeSerializer
import com.s1aks.shiftgen_dispatcher.utils.YearMonthSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.YearMonth

@Serializable
data class Shift(
    val id: Int = 0,
    val name: String,
    @Serializable(with = YearMonthSerializer::class)
    @SerialName("yearMonth")
    val periodYearMonth: YearMonth,
    val periodicity: Periodicity,
    var workerId: Int?,
    val directionId: Int,
    val action: Action,
    @Serializable(with = LocalDateTimeSerializer::class)
    val startTime: LocalDateTime,
    val duration: Long,
    val restDuration: Long
)
