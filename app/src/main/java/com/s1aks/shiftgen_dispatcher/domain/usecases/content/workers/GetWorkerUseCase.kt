package com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWorkerUseCase(
    private val repository: Repository
) {
    suspend fun execute(id: Int): ResponseState<Worker> =
        ResponseState.Success(withContext(Dispatchers.IO) { repository.getWorker(id) })
}