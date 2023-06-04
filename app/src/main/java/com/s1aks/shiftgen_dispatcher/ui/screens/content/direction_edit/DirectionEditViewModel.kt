package com.s1aks.shiftgen_dispatcher.ui.screens.content.direction_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.GetDirectionUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.InsertDirectionUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.UpdateDirectionUseCase
import com.s1aks.shiftgen_dispatcher.utils.setFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DirectionEditViewModel(
    private val getDirectionUseCase: GetDirectionUseCase,
    private val insertDirectionUseCase: InsertDirectionUseCase,
    private val updateDirectionUseCase: UpdateDirectionUseCase
) : ViewModel() {
    private val _directionState: MutableStateFlow<ResponseState<Direction>> =
        MutableStateFlow(ResponseState.Idle)
    val directionState = _directionState.asStateFlow()
    private val _responseState: MutableStateFlow<ResponseState<Boolean>> =
        MutableStateFlow(ResponseState.Idle)
    val responseState = _responseState.asStateFlow()

    fun getData(id: Int) {
        viewModelScope.setFlow(_directionState) { getDirectionUseCase.execute(id) }
    }

    fun insertData(direction: Direction) {
        viewModelScope.setFlow(_responseState) { insertDirectionUseCase.execute(direction) }
    }

    fun updateData(direction: Direction) {
        viewModelScope.setFlow(_responseState) { updateDirectionUseCase.execute(direction) }
    }

    fun clearStates() {
        _directionState.value = ResponseState.Idle
        _responseState.value = ResponseState.Idle
    }
}