package com.s1aks.shiftgen_dispatcher.domain.models

import com.s1aks.shiftgen_dispatcher.data.entities.Action

data class TimeBlockModel(
    val id: Int = 0,
    val name: String,
    val duration: Long,
    val action: Action
)
