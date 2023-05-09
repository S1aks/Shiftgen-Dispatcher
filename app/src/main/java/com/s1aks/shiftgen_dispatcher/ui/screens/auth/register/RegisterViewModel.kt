package com.s1aks.shiftgen_dispatcher.ui.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.RegisterData
import com.s1aks.shiftgen_dispatcher.data.entities.StructuresMap
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.SendRegisterDataUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.structures.GetStructuresUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val getStructuresUseCase: GetStructuresUseCase,
    private val sendRegisterDataUseCase: SendRegisterDataUseCase
) : ViewModel() {
    private val _structuresState: MutableStateFlow<ResponseState<StructuresMap>> =
        MutableStateFlow(ResponseState.Loading)
    val structuresState = _structuresState.asStateFlow()
    private val _registerState: MutableStateFlow<ResponseState<Boolean>> =
        MutableStateFlow(ResponseState.Idle)
    val registerState = _registerState.asStateFlow()

    init {
        _structuresState.value = ResponseState.Loading
        viewModelScope.launch {
            try {
                _structuresState.value = getStructuresUseCase.execute()
            } catch (exception: RuntimeException) {
                _structuresState.value = ResponseState.Error(exception)
            } finally {
                delay(200)
                _structuresState.value = ResponseState.Idle
            }
        }
    }

    fun sendData(
        registerData: RegisterData
    ) {
        _registerState.value = ResponseState.Loading
        viewModelScope.launch {
            try {
                _registerState.value = sendRegisterDataUseCase.execute(registerData)
            } catch (exception: RuntimeException) {
                _registerState.value = ResponseState.Error(exception)
            } finally {
                delay(200)
                _registerState.value = ResponseState.Idle
            }
        }
    }
}