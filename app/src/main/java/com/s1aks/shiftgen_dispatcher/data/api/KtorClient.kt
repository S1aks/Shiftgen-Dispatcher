package com.s1aks.shiftgen_dispatcher.data.api

import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.AuthCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.LoginResponse
import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.RefreshRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class KtorClient(
    private val localSecureStore: LocalSecureStore
) {
    val instance: HttpClient = HttpClient(Android) {
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                ignoreUnknownKeys = true
//                prettyPrint = true
//                isLenient = true
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
                    BearerTokens(accessToken, refreshToken)
                }
                refreshTokens {
                    val login = localSecureStore.login ?: ""
                    val refreshToken = localSecureStore.refreshToken
                    if (!refreshToken.isNullOrEmpty()) {
                        val refreshRequest = withContext(Dispatchers.IO) {
                            client.post(AuthCase.REFRESH_URL) {
                                setBody(RefreshRequest(login, refreshToken))
                            }
                        }
                        val tokensData = if (refreshRequest.status.isSuccess()) {
                            refreshRequest.body<LoginResponse>()
                        } else {
                            throw RuntimeException(refreshRequest.bodyAsText())
                        }
                        if (tokensData.accessToken.isNotBlank() && tokensData.refreshToken.isNotBlank()) {
                            localSecureStore.accessToken = tokensData.accessToken
                            localSecureStore.refreshToken = tokensData.refreshToken
                            BearerTokens(
                                tokensData.accessToken,
                                tokensData.refreshToken
                            )
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

    fun clearTokens() {
        instance.plugin(Auth).providers.filterIsInstance<BearerAuthProvider>()
            .firstOrNull()?.clearToken()
    }
}