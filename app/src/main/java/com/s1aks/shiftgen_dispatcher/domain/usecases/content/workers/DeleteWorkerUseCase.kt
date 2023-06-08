package com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteWorkerUseCase(
    private val repository: Repository
) {
    suspend fun execute(id: Int): ResponseState<List<Worker>> {
        val deleteSuccess = withContext(Dispatchers.IO) { repository.deleteWorker(id) }
        if (deleteSuccess) {
            val workers = withContext(Dispatchers.IO) { repository.getWorkers() }
            return ResponseState.Success(workers)
        } else {
            throw RuntimeException("Ошибка удаления рабочего.")
        }
    }

}