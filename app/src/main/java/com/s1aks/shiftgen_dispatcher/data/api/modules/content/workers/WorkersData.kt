package com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers

import com.s1aks.shiftgen_dispatcher.data.entities.WorkerDTO
import kotlinx.serialization.Serializable

@Serializable
data class WorkerRequest(
    val id: Int,
    val personnelNumber: Int?,
    val userId: Int?,
    val structureId: Int,
    val firstName: String,
    val lastName: String,
    val patronymic: String?,
    val accessToDirections: List<Int>?
)

@Serializable
data class WorkerResponse(
    val worker: WorkerDTO
)

@Serializable
data class WorkersResponse(
    val list: List<WorkerDTO>
)