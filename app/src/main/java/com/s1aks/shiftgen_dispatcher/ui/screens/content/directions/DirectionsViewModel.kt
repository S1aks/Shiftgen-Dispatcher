package com.s1aks.shiftgen_dispatcher.ui.screens.content.directions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.GetDirectionsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DirectionsViewModel(
    private val getDirectionsUseCase: GetDirectionsUseCase
) : ViewModel() {
    private val _directionsState: MutableStateFlow<ResponseState<List<Direction>>> =
        MutableStateFlow(ResponseState.Idle)
    val directionsState = _directionsState.asStateFlow()

    init {
        _directionsState.value = ResponseState.Loading
        viewModelScope.launch {
            try {
                _directionsState.value = getDirectionsUseCase.execute()
            } catch (exception: RuntimeException) {
                _directionsState.value = ResponseState.Error(exception)
            } finally {
                delay(200)
                _directionsState.value = ResponseState.Idle
            }
        }
    }
}