package com.s1aks.shiftgen_dispatcher.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.LoginData
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.CheckAuthorizationUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.SendLoginDataUseCase
import com.s1aks.shiftgen_dispatcher.utils.setFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(
    private val checkAuthorizationUseCase: CheckAuthorizationUseCase,
    private val sendLoginDataUseCase: SendLoginDataUseCase
) : ViewModel() {
    private val _loginState: MutableStateFlow<ResponseState<Boolean>> =
        MutableStateFlow(ResponseState.Idle)
    val loginState = _loginState.asStateFlow()

    fun sendData(loginData: LoginData) {
        viewModelScope.setFlow(_loginState) { sendLoginDataUseCase.execute(loginData) }
    }

    fun checkAuthorization() {
        viewModelScope.setFlow(_loginState) { checkAuthorizationUseCase.execute() }
    }
}