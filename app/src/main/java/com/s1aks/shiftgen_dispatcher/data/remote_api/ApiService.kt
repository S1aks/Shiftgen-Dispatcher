package com.s1aks.shiftgen_dispatcher.data.remote_api

import com.s1aks.shiftgen_dispatcher.data.remote_api.models.LoginRequest
import com.s1aks.shiftgen_dispatcher.data.remote_api.models.LoginResponse
import com.s1aks.shiftgen_dispatcher.data.remote_api.models.RefreshRequest
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

interface ApiService {

    suspend fun login(loginRequest: LoginRequest): LoginResponse?
    suspend fun refresh(refreshRequest: RefreshRequest): LoginResponse?

    companion object {
        fun create(): ApiService {
            return ApiServiceImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(json)
                        //or serializer = KotlinxSerializer()
                    }
                    install(HttpTimeout) {
                        requestTimeoutMillis = 6000L
                        connectTimeoutMillis = 6000L
                        socketTimeoutMillis = 6000L
                    }
                    install(Auth) {
                        bearer {
                            sendWithoutRequest { request -> request.url.encodedPath.startsWith("/login") }
                            // ...
                        }
                    }
                    defaultRequest {
                        headers {
                            append(HttpHeaders.Accept, "application/json")
                            append(HttpHeaders.Authorization, "Bearer *token*")
                            append(HttpHeaders.UserAgent, "ktor client")
                        }
                        contentType(ContentType.Application.Json)
                        // Parameter("api_key", "some_api_key")
                        // Content Type
                        if (method != HttpMethod.Get) contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }
                }
            )
        }

        private val json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }
    }
}
