package com.s1aks.shiftgen_dispatcher.ui.screens.auth.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.LoginData
import com.s1aks.shiftgen_dispatcher.utils.toastError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    LoginScreenUI(
        responseStateFlow = viewModel.loginState,
        onLoginClick = { loginData ->
            viewModel.sendData(loginData)
        },
        onRegisterClick = { navController.navigate("register") },
        onSuccessResponse = {
            navController.backQueue.clear()
            navController.navigate("main")
        }
    )
}

@Composable
fun LoginScreenUI(
    responseStateFlow: StateFlow<ResponseState<Boolean>>,
    onLoginClick: (LoginData) -> Unit,
    onRegisterClick: () -> Unit,
    onSuccessResponse: () -> Unit
) {
    var login by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordEnable by rememberSaveable { mutableStateOf(false) }
    var buttonLoginEnable by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var loadingState by rememberSaveable { mutableStateOf(false) }
    val responseState by responseStateFlow.collectAsState()
    when (responseState) {
        is ResponseState.Idle -> {
            loadingState = false
        }

        is ResponseState.Loading -> {
            loadingState = true
        }

        is ResponseState.Success -> {
            onSuccessResponse()
        }

        is ResponseState.Error -> {
            (responseState as ResponseState.Error).toastError(context = LocalContext.current)
        }
    }
    val checkTextFields = fun() {
        buttonLoginEnable = login.length >= 4 && password.length > 4
    }
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (loadingState) {
            CircularProgressIndicator()
        } else {
            OutlinedTextField(
                value = login,
                singleLine = true,
                onValueChange = {
                    login = it
                    passwordEnable = login.length >= 4
                    checkTextFields()
                },
                isError = login.length < 4,
                label = { Text(text = "Логин") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                )
            )
            OutlinedTextField(
                value = password,
                singleLine = true,
                onValueChange = {
                    password = it
                    checkTextFields()
                },
                isError = password.length < 4,
                label = { Text(text = "Пароль") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send,
                    keyboardType = KeyboardType.Password
                ),
                enabled = passwordEnable,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else
                        Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Спрятать пароль" else "Показать пароль"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            Row {
                Button(
                    modifier = Modifier
                        .padding(4.dp)
                        .width(120.dp),
                    onClick = { onLoginClick(LoginData(login, password)) },
                    enabled = buttonLoginEnable
                ) {
                    Text(text = "Вход")
                }
                TextButton(
                    modifier = Modifier
                        .padding(4.dp)
                        .width(120.dp),
                    onClick = onRegisterClick
                ) {
                    Text(text = "Регистрация")
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreenUI(MutableStateFlow(ResponseState.Idle), {}, {}, {})
}