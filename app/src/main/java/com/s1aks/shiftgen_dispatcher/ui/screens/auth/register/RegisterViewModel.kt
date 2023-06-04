package com.s1aks.shiftgen_dispatcher.ui.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.RegisterData
import com.s1aks.shiftgen_dispatcher.data.entities.StructuresMap
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.SendRegisterDataUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.structures.GetStructuresUseCase
import com.s1aks.shiftgen_dispatcher.utils.setFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel(
    private val getStructuresUseCase: GetStructuresUseCase,
    private val sendRegisterDataUseCase: SendRegisterDataUseCase
) : ViewModel() {
    private val _structuresState: MutableStateFlow<ResponseState<StructuresMap>> =
        MutableStateFlow(ResponseState.Loading)
    val structuresState = _structuresState.asStateFlow()
    private val _responseState: MutableStateFlow<ResponseState<Boolean>> =
        MutableStateFlow(ResponseState.Idle)
    val responseState = _responseState.asStateFlow()

    init {
        viewModelScope.setFlow(_structuresState) { getStructuresUseCase.execute() }
    }

    fun sendData(registerData: RegisterData) {
        viewModelScope.setFlow(_responseState) { sendRegisterDataUseCase.execute(registerData) }
    }
}