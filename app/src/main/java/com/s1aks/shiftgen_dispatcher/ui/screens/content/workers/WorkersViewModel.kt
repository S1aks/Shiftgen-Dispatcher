package com.s1aks.shiftgen_dispatcher.ui.screens.content.workers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers.DeleteWorkerUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers.GetWorkersUseCase
import com.s1aks.shiftgen_dispatcher.utils.setFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WorkersViewModel(
    private val getWorkersUseCase: GetWorkersUseCase,
    private val deleteWorkerUseCase: DeleteWorkerUseCase
) : ViewModel() {
    private val _workersState: MutableStateFlow<ResponseState<List<Worker>>> =
        MutableStateFlow(ResponseState.Idle)
    val workersState = _workersState.asStateFlow()

    fun getData() {
        viewModelScope.setFlow(_workersState) { getWorkersUseCase.execute() }
    }

    fun deleteData(id: Int) {
        viewModelScope.setFlow(_workersState) { deleteWorkerUseCase.execute(id) }
    }
}