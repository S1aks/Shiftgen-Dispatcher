package com.s1aks.shiftgen_dispatcher.data.remote_api

import com.s1aks.shiftgen_dispatcher.data.remote_api.models.LoginRequest
import com.s1aks.shiftgen_dispatcher.data.remote_api.models.LoginResponse
import com.s1aks.shiftgen_dispatcher.data.remote_api.models.RefreshRequest
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.utils.io.*

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

    override suspend fun login(loginRequest: LoginRequest): LoginResponse? {
        return try {
            client.post<LoginResponse> {
                url(ApiRoutes.LOGIN)
                body = loginRequest
            }
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            null
        }
    }

    override suspend fun refresh(refreshRequest: RefreshRequest): LoginResponse? {
        return try {
            client.post<LoginResponse> {
                url(ApiRoutes.REFRESH)
                body = refreshRequest
            }
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            null
        }
    }
}