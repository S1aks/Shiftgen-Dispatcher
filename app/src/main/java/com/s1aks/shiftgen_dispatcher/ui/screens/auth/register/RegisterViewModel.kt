package com.s1aks.shiftgen_dispatcher.ui.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.models.RegisterData
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.SendRegisterDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val sendRegisterDataUseCase: SendRegisterDataUseCase
) : ViewModel() {
    private val _registerState: MutableStateFlow<ResponseState<Boolean>> =
        MutableStateFlow(ResponseState.Loading)
    val registerState = _registerState.asStateFlow()

    fun sendData(
        registerData: RegisterData
    ) {
        viewModelScope.launch() {
            _registerState.emit(sendRegisterDataUseCase.execute(registerData))
        }
    }
}