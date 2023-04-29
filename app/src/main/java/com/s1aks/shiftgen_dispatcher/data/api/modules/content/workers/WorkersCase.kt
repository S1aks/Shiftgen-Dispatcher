package com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers

import com.s1aks.shiftgen_dispatcher.data.api.ApiService.Companion.BASE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.IdRequest
import io.ktor.http.HttpStatusCode

interface WorkersCase {
    suspend fun workers(): WorkersResponse
    suspend fun workerGet(idRequest: IdRequest): WorkerResponse
    suspend fun workerInsert(workerRequest: WorkerRequest): HttpStatusCode
    suspend fun workerUpdate(workerRequest: WorkerRequest): HttpStatusCode
    suspend fun workerDelete(idRequest: IdRequest): HttpStatusCode

    companion object {
        const val WORKERS_URL = "$BASE_URL/workers"
        const val WORKER_GET_URL = "$BASE_URL/worker/get"
        const val WORKER_INSERT_URL = "$BASE_URL/worker/insert"
        const val WORKER_UPDATE_URL = "$BASE_URL/worker/update"
        const val WORKER_DELETE_URL = "$BASE_URL/worker/delete"
    }
}