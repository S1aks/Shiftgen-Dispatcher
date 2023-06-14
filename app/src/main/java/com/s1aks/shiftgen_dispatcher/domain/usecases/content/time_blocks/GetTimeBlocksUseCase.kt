package com.s1aks.shiftgen_dispatcher.domain.usecases.content.time_blocks

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.TimeBlock
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTimeBlocksUseCase(
    private val repository: Repository
) {
    suspend fun execute(): ResponseState<List<TimeBlock>> =
        ResponseState.Success(withContext(Dispatchers.IO) { repository.getTimeBlocks() })
}