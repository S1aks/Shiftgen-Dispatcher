package com.s1aks.shiftgen_dispatcher.data

import com.s1aks.shiftgen_dispatcher.data.api.ApiService
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.LoginRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.RegisterRequest
import com.s1aks.shiftgen_dispatcher.domain.Repository
import com.s1aks.shiftgen_dispatcher.domain.models.TokensData

class RepositoryImpl(
    private val apiService: ApiService
) : Repository {
    override suspend fun login(login: String, password: String): TokensData =
        apiService.login(LoginRequest(login = login, password = password)).toTokensData()

    override suspend fun register(
        login: String, email: String, password: String, groupNumber: Int, structureId: Int
    ): TokensData =
        apiService.register(
            RegisterRequest(
                login = login,
                email = email,
                password = password,
                group = groupNumber,
                structureId = structureId
            )
        ).toTokensData()
}


