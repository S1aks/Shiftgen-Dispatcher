package com.s1aks.shiftgen_dispatcher.domain.models

data class Structure(
    val id: Int,
    val name: String,
    val description: String?,
    val restHours: Int,
    val allowedConsecutiveNights: Int,
    val nightStartHour: Int,
    val nightEndHour: Int
)

typealias StructureMap = Map<Int, String>