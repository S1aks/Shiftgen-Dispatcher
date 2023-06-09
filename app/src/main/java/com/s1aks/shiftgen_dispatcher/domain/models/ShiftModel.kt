package com.s1aks.shiftgen_dispatcher.domain.models

data class ShiftModel(
    val id: Int,
    val start_date: String,
    val start_time: String,
    val name: String,
    val direction: String,
    val worker: String,
    val work_time: String
)
