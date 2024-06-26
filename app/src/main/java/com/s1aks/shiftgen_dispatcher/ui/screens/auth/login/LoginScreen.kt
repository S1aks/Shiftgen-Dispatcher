package com.s1aks.shiftgen_dispatcher.ui.screens.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.s1aks.shiftgen_dispatcher.data.entities.LoginData
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.clearAndNavigate
import com.s1aks.shiftgen_dispatcher.ui.elements.LoadingIndicator
import com.s1aks.shiftgen_dispatcher.utils.onSuccess

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    LaunchedEffect(Unit) { viewModel.checkAuthorization() }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var loadingState by rememberSaveable { mutableStateOf(false) }
        val responseState by viewModel.responseState.collectAsState()
        responseState.onSuccess(LocalContext.current, { loadingState = it }) {
            navController.clearAndNavigate(Screen.Main.route)
        }
        if (loadingState) {
            LoadingIndicator()
        } else {
            LoginScreenUI(
                onLoginClick = { loginData -> viewModel.sendData(loginData) },
                onRegisterClick = { navController.navigate(Screen.Register.route) }
            )
        }
    }
}

@Composable
fun LoginScreenUI(
    onLoginClick: (LoginData) -> Unit = {},
    onRegisterClick: () -> Unit = {},
) {
    var login by rememberSaveable { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val passwordEnable by remember { derivedStateOf { login.length >= 4 } }
    val allFieldsOk by remember { derivedStateOf { login.length >= 4 && password.length >= 4 } }

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = login,
            singleLine = true,
            onValueChange = { login = it },
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
            onValueChange = { password = it },
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
                enabled = allFieldsOk
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

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreenUI()
}