package com.s1aks.shiftgen_dispatcher.domain.models

import com.s1aks.shiftgen_dispatcher.data.entities.Action

data class TimeBlock(
    val id: Int,
    val structureId: Int,
    val name: String,
    val duration: Long,
    val action: Action
)