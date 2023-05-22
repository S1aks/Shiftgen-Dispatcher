package com.s1aks.shiftgen_dispatcher.ui.screens.content.directions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.GetDirectionsUseCase
import com.s1aks.shiftgen_dispatcher.utils.setFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DirectionsViewModel(
    private val getDirectionsUseCase: GetDirectionsUseCase
) : ViewModel() {
    private val _directionsState: MutableStateFlow<ResponseState<List<Direction>>> =
        MutableStateFlow(ResponseState.Idle)
    val directionsState = _directionsState.asStateFlow()

    init {
        viewModelScope.setFlow(_directionsState) { getDirectionsUseCase.execute() }
    }
}