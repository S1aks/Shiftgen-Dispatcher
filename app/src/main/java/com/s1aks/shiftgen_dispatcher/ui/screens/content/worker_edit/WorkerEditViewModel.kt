package com.s1aks.shiftgen_dispatcher.ui.screens.content.worker_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.GetDirectionsUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers.GetWorkerUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers.InsertWorkerUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers.UpdateWorkerUseCase
import com.s1aks.shiftgen_dispatcher.utils.setFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WorkerEditViewModel(
    private val getWorkerUseCase: GetWorkerUseCase,
    private val insertWorkerUseCase: InsertWorkerUseCase,
    private val updateWorkerUseCase: UpdateWorkerUseCase,
    private val getDirectionsUseCase: GetDirectionsUseCase
) : ViewModel() {
    private val _directionsState: MutableStateFlow<ResponseState<List<Direction>>> =
        MutableStateFlow(ResponseState.Idle)
    val directionsState = _directionsState.asStateFlow()
    private val _workerState: MutableStateFlow<ResponseState<Worker>> =
        MutableStateFlow(ResponseState.Idle)
    val workerState = _workerState.asStateFlow()
    private val _responseState: MutableStateFlow<ResponseState<Boolean>> =
        MutableStateFlow(ResponseState.Idle)
    val responseState = _responseState.asStateFlow()

    init {
        viewModelScope.setFlow(_directionsState) { getDirectionsUseCase.execute() }
    }

    fun getData(id: Int) {
        viewModelScope.setFlow(_workerState) { getWorkerUseCase.execute(id) }
    }

    fun insertData(worker: Worker) {
        viewModelScope.setFlow(_responseState) { insertWorkerUseCase.execute(worker) }
    }

    fun updateData(worker: Worker) {
        viewModelScope.setFlow(_responseState) { updateWorkerUseCase.execute(worker) }
    }

    fun clearStates() {
        _directionsState.value = ResponseState.Idle
        _workerState.value = ResponseState.Idle
        _responseState.value = ResponseState.Idle
    }
}