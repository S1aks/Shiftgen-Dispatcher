package com.s1aks.shiftgen_dispatcher.ui.screens.content.directions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.DeleteDirectionUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.GetDirectionsUseCase
import com.s1aks.shiftgen_dispatcher.utils.setFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DirectionsViewModel(
    private val getDirectionsUseCase: GetDirectionsUseCase,
    private val deleteDirectionUseCase: DeleteDirectionUseCase
) : ViewModel() {
    private val _directionsState: MutableStateFlow<ResponseState<List<Direction>>> =
        MutableStateFlow(ResponseState.Idle)
    val directionsState = _directionsState.asStateFlow()

    fun getData() {
        viewModelScope.setFlow(_directionsState) { getDirectionsUseCase.execute() }
    }

    fun deleteData(id: Int) {
        viewModelScope.setFlow(_directionsState) { deleteDirectionUseCase.execute(id) }
    }

    fun clearStates() {
        _directionsState.value = ResponseState.Idle
    }
}