package com.s1aks.shiftgen_dispatcher.data.api

import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.AuthCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftsCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructuresCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks.TimeBlocksCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers.WorkersCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

interface ApiService : AuthCase, DirectionsCase, ShiftsCase, StructuresCase, TimeBlocksCase,
    TimeSheetsCase, WorkersCase {

    companion object {
        internal const val BASE_URL = "http://shiftgen.ru:8080"

        fun create(): ApiService {
            return ApiServiceImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(ContentNegotiation) {
                        json()
                    }
                    install(HttpTimeout) {
                        requestTimeoutMillis = 6000L
                        connectTimeoutMillis = 6000L
                        socketTimeoutMillis = 6000L
                    }
//                    install(Auth) {
//                        bearer {
//                            sendWithoutRequest { request -> request.url.encodedPath.startsWith("/login") }
//                            // ...
//                        }
//                    }
                    defaultRequest {
//                        headers {
//                            append(HttpHeaders.ContentType, "application/vnd.any.response+json")
//                            append(HttpHeaders.ContentType, "application/json")
//                            append(HttpHeaders.Authorization, "Bearer *token*")
//                            append(HttpHeaders.UserAgent, "ktor client")
//                        }
                        contentType(ContentType.Application.Json)
                    }
                }
            )
        }
    }
}
