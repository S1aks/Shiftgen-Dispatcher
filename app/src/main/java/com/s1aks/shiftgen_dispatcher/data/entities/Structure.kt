package com.s1aks.shiftgen_dispatcher.data.entities

import kotlinx.serialization.Serializable

typealias StructuresMap = Map<Int, String>

@Serializable
data class Structure(
    val id: Int = 0,
    val name: String,
    val description: String? = "",
    val restHours: Int = 0,
    val allowedConsecutiveNights: Int = 0,
    val nightStartHour: Int = 0,
    val nightEndHour: Int = 6
)
