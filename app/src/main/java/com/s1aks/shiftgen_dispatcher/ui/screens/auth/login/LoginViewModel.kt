package com.s1aks.shiftgen_dispatcher.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.LoginData
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.CheckAuthorizationUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.SendLoginDataUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val checkAuthorizationUseCase: CheckAuthorizationUseCase,
    private val sendLoginDataUseCase: SendLoginDataUseCase
) : ViewModel() {
    private val _loginState: MutableStateFlow<ResponseState<Boolean>> =
        MutableStateFlow(ResponseState.Idle)
    val loginState = _loginState.asStateFlow()

    fun sendData(loginData: LoginData) {
        _loginState.value = ResponseState.Loading
        viewModelScope.launch {
            try {
                _loginState.value = sendLoginDataUseCase.execute(loginData)
            } catch (exception: RuntimeException) {
                _loginState.value = ResponseState.Error(exception)
            } finally {
                delay(200)
                _loginState.value = ResponseState.Idle
            }
        }
    }

    fun checkAuthorization() {
        _loginState.value = ResponseState.Loading
        viewModelScope.launch {
            try {
                _loginState.value = checkAuthorizationUseCase.execute()
            } catch (exception: RuntimeException) {
                _loginState.value = ResponseState.Error(exception)
            } finally {
                delay(200)
                _loginState.value = ResponseState.Idle
            }
        }
    }
}