package com.s1aks.shiftgen_dispatcher.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Worker(
    val id: Int = 0,
    val personnelNumber: Int?,
    val userId: Int?,
    val firstName: String,
    val lastName: String,
    val patronymic: String?,
    val accessToDirections: List<Int>?
)
