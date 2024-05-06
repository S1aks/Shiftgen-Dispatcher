package com.s1aks.shiftgen_dispatcher.domain.models

data class ShiftItemModel(
    val id: Int,
    val name: String,
    val worker: String,
    val direction: String,
    val startDay: String,
    val startTime: String,
    val workTime: String
)
