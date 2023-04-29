package com.s1aks.shiftgen_dispatcher.ui.screens.auth.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.models.RegisterData
import com.s1aks.shiftgen_dispatcher.utils.isValidEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    RegisterScreenUI(
        responseStateFlow = MutableStateFlow(ResponseState.Loading).asStateFlow(),//viewModel.loginState,
        onRegisterClick = { registerData -> viewModel.sendData(registerData) },
        onCancelClick = { navController.popBackStack() },
        onSuccessResponse = {
            navController.backQueue.clear()
            navController.navigate("main")
        }
    )
}

@Composable
fun RegisterScreenUI(
    responseStateFlow: StateFlow<ResponseState<Boolean>>,
    onRegisterClick: (RegisterData) -> Unit,
    onCancelClick: () -> Unit,
    onSuccessResponse: () -> Unit
) {
    var login by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var group by rememberSaveable { mutableStateOf("") }
    var structure by rememberSaveable { mutableStateOf("") }
    var emailValid by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var buttonRegisterEnable by rememberSaveable { mutableStateOf(false) }
    val checkTextFields = fun() {
        emailValid = email.isNotBlank() && email.isValidEmail()
        buttonRegisterEnable = login != ""
                && emailValid
                && password != ""
                && password.length > 4
                && group != ""
                && structure != ""
    }
    val responseState by responseStateFlow.collectAsState()
    if (responseState == ResponseState.Success(true) && password.isNotBlank()) {
        password = "" // Флаг перехода для препятствия зацикливания при рекомпозиции
        onSuccessResponse()
    } // todo Do business logic!!!!!
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
            onValueChange = {
                login = it
                checkTextFields()
            },
            label = { Text(text = "Логин") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            )
        )
        OutlinedTextField(
            value = email,
            singleLine = true,
            onValueChange = {
                email = it
                checkTextFields()
            },
            isError = !emailValid && email.isNotEmpty(),
            label = { Text(text = "E-mail") },
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
            label = { Text(text = "Пароль") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
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
        OutlinedTextField(
            value = group,
            singleLine = true,
            onValueChange = {
                group = it
                checkTextFields()
            },
            label = { Text(text = "Группа") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            )
        )
        OutlinedTextField(
            value = structure,
            singleLine = true,
            onValueChange = {
                structure = it
                checkTextFields()
            },
            label = { Text(text = "Структура") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send,
                keyboardType = KeyboardType.Text
            )
        )
        Row {
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .width(130.dp),
                onClick = {
                    onRegisterClick(RegisterData(login, email, password, group, structure))
                },
                enabled = buttonRegisterEnable
            ) {
                Text(text = "Регистрация")
            }
            TextButton(
                modifier = Modifier
                    .padding(4.dp)
                    .width(110.dp),
                onClick = onCancelClick
            ) {
                Text(text = "Отмена")
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreenUI(MutableStateFlow(ResponseState.Loading), {}, {}, {})
}