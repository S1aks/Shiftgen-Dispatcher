package com.s1aks.shiftgen_dispatcher.data.remote_api.models

import kotlinx.serialization.Serializable

@Serializable
data class IdRequest(
    val id: Int
)