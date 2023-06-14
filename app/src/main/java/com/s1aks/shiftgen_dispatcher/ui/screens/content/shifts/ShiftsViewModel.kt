package com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.models.ShiftItemModel
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts.DeleteShiftUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts.GetShiftsUseCase
import com.s1aks.shiftgen_dispatcher.utils.setFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.YearMonth

class ShiftsViewModel(
    private val getShiftsUseCase: GetShiftsUseCase,
    private val deleteShiftUseCase: DeleteShiftUseCase
) : ViewModel() {
    private val _shiftsState: MutableStateFlow<ResponseState<List<ShiftItemModel>>> =
        MutableStateFlow(ResponseState.Idle)
    val shiftsState = _shiftsState.asStateFlow()

    fun getData(yearMonth: YearMonth) {
        viewModelScope.setFlow(_shiftsState) { getShiftsUseCase.execute(yearMonth) }
    }

    fun deleteData(id: Int, yearMonth: YearMonth) {
        viewModelScope.setFlow(_shiftsState) { deleteShiftUseCase.execute(id, yearMonth) }
    }

    fun clearStates() {
        _shiftsState.value = ResponseState.Idle
    }
}