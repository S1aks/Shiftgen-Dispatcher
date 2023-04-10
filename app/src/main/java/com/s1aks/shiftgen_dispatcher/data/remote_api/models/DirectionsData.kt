package com.s1aks.shiftgen_dispatcher.data.remote_api.models

import com.s1aks.shiftgen_dispatcher.data.entities.DirectionDTO
import kotlinx.serialization.Serializable

@Serializable
data class DirectionRequest(
    val id: Int,
    val name: String
)

@Serializable
data class DirectionResponse(
    val direction: DirectionDTO
)

@Serializable
data class DirectionsResponse(
    val list: List<DirectionDTO>
)