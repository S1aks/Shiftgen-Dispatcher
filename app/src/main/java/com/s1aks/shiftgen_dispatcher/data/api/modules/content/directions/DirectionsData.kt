package com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions

import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import kotlinx.serialization.Serializable

@Serializable
data class DirectionRequest(
    val id: Int,
    val name: String
)

@Serializable
data class DirectionResponse(
    val direction: Direction
)

@Serializable
data class DirectionsResponse(
    val list: List<Direction>
)