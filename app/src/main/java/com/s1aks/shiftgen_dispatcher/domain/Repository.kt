package com.s1aks.shiftgen_dispatcher.domain

import com.s1aks.shiftgen_dispatcher.domain.models.TokensData

interface Repository {
    suspend fun login(login: String, password: String): TokensData
}