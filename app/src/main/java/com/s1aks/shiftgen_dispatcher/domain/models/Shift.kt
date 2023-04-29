package com.s1aks.shiftgen_dispatcher.domain.models

import com.s1aks.shiftgen_dispatcher.data.entities.Periodicity
import java.time.LocalDateTime
import java.time.YearMonth

data class Shift(
    val id: Int,
    val name: String,
    val periodYearMonth: YearMonth,
    val periodicity: Periodicity,
    var workerId: Int?,
    val structureId: Int,
    val directionId: Int,
    val startTime: LocalDateTime,
    val timeBlocksIds: List<Int>
)