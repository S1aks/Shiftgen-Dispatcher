package com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts

import androidx.lifecycle.ViewModel
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Shift
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShiftsViewModel : ViewModel() {
    private val _shiftsState: MutableStateFlow<ResponseState<List<Shift>>> =
        MutableStateFlow(ResponseState.Idle)
    val shiftsState = _shiftsState.asStateFlow()
}