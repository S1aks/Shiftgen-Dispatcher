package com.s1aks.shiftgen_dispatcher.data.api

import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.AuthCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.AuthCase.Companion.REFRESH_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.LoginResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.RefreshRequest
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftsCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructuresCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks.TimeBlocksCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers.WorkersCase
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent.inject

interface ApiService : AuthCase, DirectionsCase, ShiftsCase, StructuresCase, TimeBlocksCase,
    TimeSheetsCase, WorkersCase {

    companion object {
        internal const val BASE_URL = "http://shiftgen.ru:8080"

        fun create(): ApiService = ApiServiceImpl(
            client = HttpClient(Android) {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    })
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 6000L
                    connectTimeoutMillis = 6000L
                    socketTimeoutMillis = 6000L
                }
                expectSuccess = false
                install(Auth) {
                    bearer {
                        val localSecureStore: LocalSecureStore by inject(LocalSecureStore::class.java)
                        sendWithoutRequest { request ->
                            val path = request.url.encodedPath
                            path != "/auth/login"
                                    && path != "/auth/register"
                                    && path != "/auth/refresh"
                                    && path != "/structures"
                                    && path != "/insert/structure"
                        }
                        loadTokens {
                            val accessToken = localSecureStore.accessToken ?: ""
                            val refreshToken = localSecureStore.refreshToken ?: ""
                            // Load tokens from a local storage and return them as the 'BearerTokens' instance
                            BearerTokens(accessToken, refreshToken)
                        }
                        refreshTokens {
                            val login = localSecureStore.login ?: ""
                            val refreshToken = localSecureStore.refreshToken
                            if (!refreshToken.isNullOrEmpty()) {
                                val tokensData = client.post(REFRESH_URL) {
                                    setBody(RefreshRequest(login, refreshToken))
                                }
                                    .body<LoginResponse>()
                                if (tokensData.accessToken.isNotBlank() && tokensData.refreshToken.isNotBlank()) {
                                    localSecureStore.accessToken = tokensData.accessToken
                                    localSecureStore.refreshToken = tokensData.refreshToken
                                    BearerTokens(tokensData.accessToken, tokensData.refreshToken)
                                } else {
                                    throw RuntimeException("Ошибка получения токена.")
                                }
                            } else {
                                BearerTokens(accessToken = "", refreshToken = "")
                            }
                        }
                    }
                }
                defaultRequest {
                    headers {
                        append(HttpHeaders.UserAgent, "shiftgen dispatcher client")
                    }
                    contentType(ContentType.Application.Json)
                }
            }
        )
    }
}

