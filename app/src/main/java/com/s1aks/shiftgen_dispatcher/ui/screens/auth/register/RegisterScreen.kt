package com.s1aks.shiftgen_dispatcher.ui.screens.auth.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Groups
import com.s1aks.shiftgen_dispatcher.data.entities.RegisterData
import com.s1aks.shiftgen_dispatcher.data.entities.StructuresMap
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.clearAndNavigate
import com.s1aks.shiftgen_dispatcher.ui.elements.LoadingIndicator
import com.s1aks.shiftgen_dispatcher.utils.inc
import com.s1aks.shiftgen_dispatcher.utils.isValidEmail
import com.s1aks.shiftgen_dispatcher.utils.onSuccess
import com.s1aks.shiftgen_dispatcher.utils.setOutlinedTextFieldBorderColor
import com.s1aks.shiftgen_dispatcher.utils.setOutlinedTextFieldColorsWithThis
import com.s1aks.shiftgen_dispatcher.utils.setOutlinedTextFieldIconByExpanded
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val screenState: RegisterScreenState by remember {
            mutableStateOf(RegisterScreenState(structureLoading = false, structuresMap = mapOf()))
        }
        var loadingState by rememberSaveable { mutableStateOf(false) }
        val structuresState by viewModel.structuresState.collectAsState()
        structuresState.onSuccess(LocalContext.current, { screenState.structureLoading = it }) {
            val struct = (structuresState as ResponseState.Success).item
            screenState.structuresMap = struct
        }
        val responseState by viewModel.responseState.collectAsState()
        responseState.onSuccess(LocalContext.current, { loadingState = it }) {
            navController.clearAndNavigate(Screen.Main.route)
        }
        LaunchedEffect(Unit) {
            viewModel.getStructures()
        }
        if (loadingState) {
            LoadingIndicator()
        } else {
            RegisterScreenUI(
                screenState = screenState,
                onRegisterClick = { registerData -> viewModel.sendData(registerData) },
                onCancelClick = { navController.popBackStack() }
            )
        }
    }
}

data class RegisterScreenState(
    var structureLoading: Boolean,
    var structuresMap: StructuresMap
)

@Composable
fun RegisterScreenUI(
    screenState: RegisterScreenState = RegisterScreenState(false, mapOf()),
    onRegisterClick: (RegisterData) -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    var login by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var group by rememberSaveable { mutableStateOf("") }
    var structure by rememberSaveable { mutableStateOf("") }
    var pin by rememberSaveable { mutableStateOf("") }
    var emailValid by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var structureEnable by rememberSaveable { mutableStateOf(false) }
    val groupsList = Groups.values().drop(1).map { it.groupName }
    val loginFieldOk = fun(): Boolean = login.length >= 4
    val emailFieldOk = fun(): Boolean = emailValid
    val passwordFieldOk = fun(): Boolean = password.length >= 4
    val groupFieldOk = fun(): Boolean = group in groupsList
    val structureFieldOk = fun(): Boolean =
        if (structureEnable) structure.length >= 4 && structure !in screenState.structuresMap.keys
        else structure in screenState.structuresMap.keys
    val pinFieldOk = fun(): Boolean = if (!structureEnable) pin.length == 5 else true
    val allFieldsOk by remember {
        derivedStateOf {
            loginFieldOk()
                    && emailFieldOk()
                    && passwordFieldOk()
                    && groupFieldOk()
                    && structureFieldOk()
                    && pinFieldOk()
        }
    }
    var expandedGroup by rememberSaveable { mutableStateOf(false) }
    var expandedStructure by rememberSaveable { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val iconGroup = expandedGroup.setOutlinedTextFieldIconByExpanded()
    val iconStructure = expandedStructure.setOutlinedTextFieldIconByExpanded()
    val groupColor = group.setOutlinedTextFieldBorderColor()
    val structureColor = structure.setOutlinedTextFieldBorderColor()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

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
            isError = !loginFieldOk(),
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
                emailValid = email.isNotBlank() && email.isValidEmail()
            },
            isError = !emailFieldOk(),
            label = { Text(text = "E-mail") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            )
        )
        OutlinedTextField(
            value = password,
            singleLine = true,
            onValueChange = { password = it },
            isError = !passwordFieldOk(),
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
        Box {
            OutlinedTextField(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
                value = group,
                singleLine = true,
                onValueChange = { group = it },
                label = { Text(text = "Группа") },
                trailingIcon = {
                    Icon(
                        iconGroup, "Группа пользователя",
                        Modifier.clickable { expandedGroup++ },
                        tint = groupColor
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                enabled = false,
                colors = groupColor.setOutlinedTextFieldColorsWithThis()
            )
            DropdownMenu(
                expanded = expandedGroup,
                onDismissRequest = { expandedGroup = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
            ) {
                groupsList.forEach { groupName ->
                    DropdownMenuItem(
                        onClick = {
                            group = groupName
                            expandedGroup = false
                            focusManager.clearFocus()
                        }
                    ) { Text(text = groupName) }
                }
            }
        }
        Box {
            OutlinedTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = structure,
                singleLine = true,
                onValueChange = { structure = it },
                isError = !structureFieldOk(),
                label = { Text(text = "Структура") },
                trailingIcon = {
                    if (screenState.structureLoading) CircularProgressIndicator(color = structureColor)
                    else
                        Icon(
                            iconStructure, "Группа пользователя",
                            Modifier.clickable { expandedStructure++ },
                            tint = structureColor
                        )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send,
                    keyboardType = KeyboardType.Text
                ),
                enabled = structureEnable,
                colors = structureColor.setOutlinedTextFieldColorsWithThis()
            )
            DropdownMenu(
                expanded = expandedStructure,
                onDismissRequest = { expandedStructure = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
            ) {
                screenState.structuresMap.forEach { structureItem ->
                    DropdownMenuItem(
                        onClick = {
                            expandedStructure = false
                            structureEnable = structureItem.value == 0
                            structure = if (structureEnable) "" else structureItem.key
                            focusManager.clearFocus()
                        }
                    ) { Text(text = structureItem.key) }
                }
            }
            LaunchedEffect(structureEnable) {
                if (structureEnable) {
                    delay(200)
                    focusRequester.requestFocus()
                }
            }
        }
        if (!structureEnable && structure.isNotBlank()) {
            OutlinedTextField(
                value = pin,
                singleLine = true,
                onValueChange = { pin = it },
                isError = !pinFieldOk(),
                label = { Text(text = "Пин диспетчера") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )
        }
        Row {
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .width(140.dp),
                onClick = {
                    onRegisterClick(
                        RegisterData(login, email, password, group, structure, pin)
                    )
                },
                enabled = allFieldsOk
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

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreenUI()
}