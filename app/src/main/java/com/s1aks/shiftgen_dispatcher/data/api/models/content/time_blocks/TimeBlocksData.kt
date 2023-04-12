package com.s1aks.shiftgen_dispatcher.data.api.models.content.time_blocks

import com.s1aks.shiftgen_dispatcher.data.entities.Action
import com.s1aks.shiftgen_dispatcher.data.entities.TimeBlockDTO
import kotlinx.serialization.Serializable

@Serializable
data class TimeBlockRequest(
    val id: Int,
    val structureId: Int,
    val name: String,
    val duration: Long,
    val action: Action
)

@Serializable
data class TimeBlockResponse(
    val timeBlock: TimeBlockDTO
)

@Serializable
data class TimeBlocksResponse(
    val list: List<TimeBlockDTO>
)