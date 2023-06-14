package com.s1aks.shiftgen_dispatcher.domain.usecases.content.time_blocks

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.TimeBlock
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteTimeBlockUseCase(
    private val repository: Repository,
    private val getTimeBlocksUseCase: GetTimeBlocksUseCase
) {
    suspend fun execute(id: Int): ResponseState<List<TimeBlock>> {
        val deleteSuccess = withContext(Dispatchers.IO) { repository.deleteTimeBlock(id) }
        if (deleteSuccess) {
            return getTimeBlocksUseCase.execute()
        } else {
            throw RuntimeException("Ошибка удаления временного блока.")
        }
    }

}