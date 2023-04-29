package com.s1aks.shiftgen_dispatcher.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class DirectionDTO(
    val id: Int = 0,
    val name: String,
    val structureId: Int
)
