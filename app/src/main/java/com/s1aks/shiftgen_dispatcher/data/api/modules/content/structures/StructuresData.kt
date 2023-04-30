package com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures

import com.s1aks.shiftgen_dispatcher.data.entities.Structure
import com.s1aks.shiftgen_dispatcher.data.entities.StructuresMap
import kotlinx.serialization.Serializable

@Serializable
data class StructureRequest(
    val id: Int,
    val name: String,
    val description: String?,
    val restHours: Int,
    val allowedConsecutiveNights: Int,
    val nightStartHour: Int,
    val nightEndHour: Int
)

@Serializable
data class StructureResponse(
    val structure: Structure
)

@Serializable
data class StructuresResponse(
    val list: StructuresMap
)