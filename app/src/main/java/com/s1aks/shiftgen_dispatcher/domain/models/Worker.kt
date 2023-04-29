package com.s1aks.shiftgen_dispatcher.domain.models

data class Worker(
    val id: Int,
    val personnelNumber: Int?,
    val userId: Int?,
    val structureId: Int,
    val firstName: String,
    val lastName: String,
    val patronymic: String?,
    val accessToDirections: List<Int>?
)