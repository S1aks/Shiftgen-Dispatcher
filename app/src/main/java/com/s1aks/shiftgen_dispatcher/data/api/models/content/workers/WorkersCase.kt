package com.s1aks.shiftgen_dispatcher.data.api.models.content.workers

import com.s1aks.shiftgen_dispatcher.data.api.ApiService.Companion.BASE_URL
import com.s1aks.shiftgen_dispatcher.data.api.models.content.IdRequest

interface WorkersCase {
    suspend fun workers(): WorkersResponse
    suspend fun workerGet(idRequest: IdRequest): WorkerResponse
    suspend fun workerInsert(workerRequest: WorkerRequest)
    suspend fun workerUpdate(workerRequest: WorkerRequest)
    suspend fun workerDelete(idRequest: IdRequest)

    companion object {
        const val WORKERS = "$BASE_URL/workers"
        const val WORKER_GET = "$BASE_URL/worker/get"
        const val WORKER_INSERT = "$BASE_URL/worker/insert"
        const val WORKER_UPDATE = "$BASE_URL/worker/update"
        const val WORKER_DELETE = "$BASE_URL/worker/delete"
    }
}