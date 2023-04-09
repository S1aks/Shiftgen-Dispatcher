package com.s1aks.shiftgen_dispatcher.ui.screens.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.s1aks.shiftgen_dispatcher.ui.screens.LoadingScreen

@Composable
fun LoginScreen(navController: NavController) {
    var login by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordEnable by rememberSaveable { mutableStateOf(false) }
    var buttonLoginEnable by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
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
                passwordEnable = it != ""
                login = it
                buttonLoginEnable = login != "" && password != "" && password.length > 5
            },
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
                buttonLoginEnable = login != "" && password != "" && password.length > 5
            },
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
        Row() {
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .width(120.dp),
                onClick = {
                    // viewmodel.sendLoginData(login, password)
                },
                enabled = buttonLoginEnable
            ) {
                Text(text = "Вход")
            }
            TextButton(
                modifier = Modifier
                    .padding(4.dp)
                    .width(120.dp),
                onClick = { navController.navigate("register") }
            ) {
                Text(text = "Регистрация")
            }
        }
    }
}
