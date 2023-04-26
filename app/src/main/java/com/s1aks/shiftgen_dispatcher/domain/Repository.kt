package com.s1aks.shiftgen_dispatcher.domain

import com.s1aks.shiftgen_dispatcher.domain.models.TokensData

interface Repository {
    suspend fun login(login: String, password: String): TokensData
    suspend fun register(
        login: String, email: String, password: String, groupNumber: Int, structureId: Int
    ): TokensData

}