package com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Shift
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts.GetShiftsUseCase
import com.s1aks.shiftgen_dispatcher.utils.setFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.YearMonth

class ShiftsViewModel(
    private val getShiftsUseCase: GetShiftsUseCase
) : ViewModel() {
    private val _shiftsState: MutableStateFlow<ResponseState<List<Shift>>> =
        MutableStateFlow(ResponseState.Idle)
    val shiftsState = _shiftsState.asStateFlow()

    init {
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {   // todo Update usage YearMonth.now
        viewModelScope.setFlow(_shiftsState) { getShiftsUseCase.execute(YearMonth.now()) }
    }
}