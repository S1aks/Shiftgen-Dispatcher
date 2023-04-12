package com.s1aks.shiftgen_dispatcher.data.api

import com.s1aks.shiftgen_dispatcher.data.api.models.auth.AuthCase
import com.s1aks.shiftgen_dispatcher.data.api.models.content.directions.DirectionsCase
import com.s1aks.shiftgen_dispatcher.data.api.models.content.shifts.ShiftsCase
import com.s1aks.shiftgen_dispatcher.data.api.models.content.structures.StructuresCase
import com.s1aks.shiftgen_dispatcher.data.api.models.content.time_blocks.TimeBlocksCase
import com.s1aks.shiftgen_dispatcher.data.api.models.content.timesheets.TimeSheetsCase
import com.s1aks.shiftgen_dispatcher.data.api.models.content.workers.WorkersCase
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

interface ApiService : AuthCase, DirectionsCase, ShiftsCase, StructuresCase, TimeBlocksCase,
    TimeSheetsCase, WorkersCase {

    companion object {
        internal const val BASE_URL = "http://shiftgen.ru"

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
