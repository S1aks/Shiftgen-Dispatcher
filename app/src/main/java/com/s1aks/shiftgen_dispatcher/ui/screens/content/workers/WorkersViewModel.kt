package com.s1aks.shiftgen_dispatcher.ui.screens.content.workers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers.GetWorkersUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WorkersViewModel(
    private val getWorkersUseCase: GetWorkersUseCase
) : ViewModel() {
    private val _workersState: MutableStateFlow<ResponseState<List<Worker>>> =
        MutableStateFlow(ResponseState.Idle)
    val workersState = _workersState.asStateFlow()

    init {
        _workersState.value = ResponseState.Loading
        viewModelScope.launch {
            try {
                _workersState.value = getWorkersUseCase.execute()
            } catch (exception: RuntimeException) {
                _workersState.value = ResponseState.Error(exception)
            } finally {
                delay(200)
                _workersState.value = ResponseState.Idle
            }
        }
    }
}