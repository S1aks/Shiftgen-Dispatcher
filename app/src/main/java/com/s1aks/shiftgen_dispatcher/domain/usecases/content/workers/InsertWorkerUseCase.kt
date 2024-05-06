package com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertWorkerUseCase(
    private val repository: Repository
) {
    suspend fun execute(worker: Worker): ResponseState<Boolean> =
        ResponseState.Success(withContext(Dispatchers.IO) { repository.insertWorker(worker) })
}