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
    private val _responseState: MutableStateFlow<ResponseState<Boolean>> =
        MutableStateFlow(ResponseState.Idle)
    val responseState = _responseState.asStateFlow()

    fun sendData(loginData: LoginData) {
        viewModelScope.setFlow(_responseState) { sendLoginDataUseCase.execute(loginData) }
    }

    fun checkAuthorization() {
        viewModelScope.setFlow(_responseState) { checkAuthorizationUseCase.execute() }
    }
}