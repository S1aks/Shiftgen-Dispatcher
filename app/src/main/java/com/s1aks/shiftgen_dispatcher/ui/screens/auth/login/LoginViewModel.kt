package com.s1aks.shiftgen_dispatcher.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.models.LoginData
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.SendLoginDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sendLoginDataUseCase: SendLoginDataUseCase
) : ViewModel() {
    private val _loginState: MutableStateFlow<ResponseState<Boolean>> =
        MutableStateFlow(ResponseState.Loading)
    val loginState = _loginState.asStateFlow()

    fun sendData(loginData: LoginData) {
        viewModelScope.launch {
            _loginState.emit(sendLoginDataUseCase.execute(loginData))
        }
    }
}