package com.s1aks.shiftgen_dispatcher.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class TimeBlock(
    val id: Int = 0,
    val name: String,
    val duration: Long,
    val action: Action
)
