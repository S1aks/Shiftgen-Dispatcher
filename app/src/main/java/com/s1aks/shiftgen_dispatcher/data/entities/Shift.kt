package com.s1aks.shiftgen_dispatcher.data.entities

import com.s1aks.shiftgen_dispatcher.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Shift(
    val id: Int = 0,
    val name: String,
    val periodicity: Periodicity,
    var workerId: Int?,
    val manualWorkerSelection: Boolean = false,
    val directionId: Int,
    val action: Action,
    @Serializable(with = LocalDateTimeSerializer::class)
    val startTime: LocalDateTime,
    val duration: Long,
    val restDuration: Long
)
