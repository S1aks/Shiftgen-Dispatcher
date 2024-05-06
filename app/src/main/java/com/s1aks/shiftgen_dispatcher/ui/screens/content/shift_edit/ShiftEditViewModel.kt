package com.s1aks.shiftgen_dispatcher.ui.screens.content.shift_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.data.entities.Shift
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.GetDirectionsUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts.GetShiftUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts.InsertShiftUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts.UpdateShiftUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers.GetWorkersUseCase
import com.s1aks.shiftgen_dispatcher.utils.setFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShiftEditViewModel(
    private val getShiftUseCase: GetShiftUseCase,
    private val insertShiftUseCase: InsertShiftUseCase,
    private val updateShiftUseCase: UpdateShiftUseCase,
    private val getDirectionsUseCase: GetDirectionsUseCase,
    private val getWorkersUseCase: GetWorkersUseCase
) : ViewModel() {
    private val _directionsState: MutableStateFlow<ResponseState<List<Direction>>> =
        MutableStateFlow(ResponseState.Idle)
    val directionsState = _directionsState.asStateFlow()
    private val _workersState: MutableStateFlow<ResponseState<List<Worker>>> =
        MutableStateFlow(ResponseState.Idle)
    val workersState = _workersState.asStateFlow()
    private val _shiftState: MutableStateFlow<ResponseState<Shift>> =
        MutableStateFlow(ResponseState.Idle)
    val shiftState = _shiftState.asStateFlow()
    private val _responseState: MutableStateFlow<ResponseState<Boolean>> =
        MutableStateFlow(ResponseState.Idle)
    val responseState = _responseState.asStateFlow()

    fun getBasicData() {
        viewModelScope.setFlow(_directionsState) { getDirectionsUseCase.execute() }
        viewModelScope.setFlow(_workersState) { getWorkersUseCase.execute() }
    }

    fun getShiftData(id: Int) {
        viewModelScope.setFlow(_shiftState) { getShiftUseCase.execute(id) }
    }

    fun insertData(shiftModel: Shift) {
        viewModelScope.setFlow(_responseState) { insertShiftUseCase.execute(shiftModel) }
    }

    fun updateData(shiftModel: Shift) {
        viewModelScope.setFlow(_responseState) { updateShiftUseCase.execute(shiftModel) }
    }

    fun clearStates() {
        _shiftState.value = ResponseState.Idle
        _directionsState.value = ResponseState.Idle
        _workersState.value = ResponseState.Idle
        _responseState.value = ResponseState.Idle
    }
}