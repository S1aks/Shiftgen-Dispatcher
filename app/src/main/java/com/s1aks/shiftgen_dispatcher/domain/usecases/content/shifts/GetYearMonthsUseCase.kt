package com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts

import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetYearMonthsUseCase(
    private val repository: Repository
) {
    suspend fun execute(): ResponseState<List<String>> {
        val yearMonths = withContext(Dispatchers.IO) { repository.getYearMonths() }
        return ResponseState.Success(yearMonths)
    }
}