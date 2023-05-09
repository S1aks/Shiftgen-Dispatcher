package com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Shift
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts.GetShiftsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth

class ShiftsViewModel(
    private val getShiftsUseCase: GetShiftsUseCase
) : ViewModel() {
    private val _shiftsState: MutableStateFlow<ResponseState<List<Shift>>> =
        MutableStateFlow(ResponseState.Idle)
    val shiftsState = _shiftsState.asStateFlow()

    init {
        _shiftsState.value = ResponseState.Loading
        viewModelScope.launch {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {   // todo Update usage YearMonth.now
                    _shiftsState.value = getShiftsUseCase.execute(YearMonth.now())
                }
            } catch (exception: RuntimeException) {
                _shiftsState.value = ResponseState.Error(exception)
            } finally {
                delay(200)
                _shiftsState.value = ResponseState.Idle
            }
        }
    }
}