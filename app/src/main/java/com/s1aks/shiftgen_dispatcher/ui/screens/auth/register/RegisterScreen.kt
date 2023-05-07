package com.s1aks.shiftgen_dispatcher.ui.screens.auth.register

import android.annotation.SuppressLint
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
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.graphics.Color
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
import com.s1aks.shiftgen_dispatcher.ui.NAV_MAIN
import com.s1aks.shiftgen_dispatcher.utils.isValidEmail
import com.s1aks.shiftgen_dispatcher.utils.toastError
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    RegisterScreenUI(
        structuresStateFlow = viewModel.structuresState,
        responseStateFlow = viewModel.registerState,
        onRegisterClick = { registerData -> viewModel.sendData(registerData) },
        onCancelClick = { navController.popBackStack() },
        onSuccessResponse = {
            navController.backQueue.clear()
            navController.navigate(NAV_MAIN)
        }
    )
}

@Composable
fun RegisterScreenUI(
    structuresStateFlow: StateFlow<ResponseState<StructuresMap>>,
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
    var structureEnable by rememberSaveable { mutableStateOf(false) }
    var buttonRegisterEnable by rememberSaveable { mutableStateOf(false) }
    val groupsList = Groups.values().drop(1).map { it.groupName }
    var structuresMap: StructuresMap by rememberSaveable { mutableStateOf(mapOf()) }
    var structureLoading by rememberSaveable { mutableStateOf(false) }
    val structuresState by structuresStateFlow.collectAsState()
    when (structuresState) {
        ResponseState.Idle -> {
            structureLoading = false
        }

        ResponseState.Loading -> {
            structureLoading = true
        }

        is ResponseState.Success -> {
            structuresMap = (structuresState as ResponseState.Success).item
        }

        is ResponseState.Error -> {
            (structuresState as ResponseState.Error).toastError(context = LocalContext.current)
        }
    }
    var loadingState by rememberSaveable { mutableStateOf(false) }
    val responseState by responseStateFlow.collectAsState()
    when (responseState) {
        ResponseState.Idle -> {
            loadingState = false
        }

        ResponseState.Loading -> {
            loadingState = true
        }

        is ResponseState.Success -> {
            onSuccessResponse()
        }

        is ResponseState.Error -> {
            (responseState as ResponseState.Error).toastError(context = LocalContext.current)
        }
    }
    val loginFieldOk = fun(): Boolean = login.length >= 4
    val emailFieldOk = fun(): Boolean = emailValid
    val passwordFieldOk = fun(): Boolean = password.length >= 4
    val groupFieldOk = fun(): Boolean = group in groupsList
    val structureFieldOk = fun(): Boolean =
        if (structureEnable) structure.length >= 4 && structure !in structuresMap.values
        else structure in structuresMap.values
    val checkTextFields = fun() {
        buttonRegisterEnable = loginFieldOk()
                && emailFieldOk()
                && passwordFieldOk()
                && groupFieldOk()
                && structureFieldOk()
    }
    var expandedGroup by rememberSaveable { mutableStateOf(false) }
    var expandedStructure by rememberSaveable { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val iconGroup = if (expandedGroup) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val iconStructure =
        if (expandedStructure) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val groupColor =
        if (group.isEmpty()) MaterialTheme.colors.error.copy(ContentAlpha.high)
        else MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled)
    val structureColor =
        if (structure.isEmpty()) MaterialTheme.colors.error.copy(ContentAlpha.high)
        else MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled)
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

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
                    checkTextFields()
                },
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
                    checkTextFields()
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
                onValueChange = {
                    password = it
                    checkTextFields()
                },
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
                        //This value is used to assign to the DropDown the same width
                        textFieldSize = coordinates.size.toSize()
                    },
                    value = group,
                    singleLine = true,
                    onValueChange = {
                        group = it
                        checkTextFields()
                    },
                    label = { Text(text = "Группа") },
                    trailingIcon = {
                        Icon(
                            iconGroup, "Группа пользователя",
                            Modifier.clickable { expandedGroup = !expandedGroup },
                            tint = groupColor
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    enabled = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                        backgroundColor = Color.Transparent,
                        disabledBorderColor = groupColor,
                        disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                    )
                )
                DropdownMenu(
                    expanded = expandedGroup,
                    onDismissRequest = { expandedGroup = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                ) {
                    groupsList.forEach { groupName ->
                        DropdownMenuItem(onClick = {
                            group = groupName
                            expandedGroup = false
                            focusManager.clearFocus()
                            checkTextFields()
                        }) {
                            Text(text = groupName)
                        }
                    }
                }
            }
            Box {
                OutlinedTextField(
                    modifier = Modifier.focusRequester(focusRequester),
                    value = structure,
                    singleLine = true,
                    onValueChange = {
                        structure = it
                        checkTextFields()
                    },
                    isError = !structureFieldOk(),
                    label = { Text(text = "Структура") },
                    trailingIcon = {
                        if (structureLoading) CircularProgressIndicator(color = structureColor)
                        else
                            Icon(
                                iconStructure, "Группа пользователя",
                                Modifier.clickable { expandedStructure = !expandedStructure },
                                tint = structureColor
                            )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Send,
                        keyboardType = KeyboardType.Text
                    ),
                    enabled = structureEnable,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                        backgroundColor = Color.Transparent,
                        disabledBorderColor = structureColor,
                        disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                    )
                )
                DropdownMenu(
                    expanded = expandedStructure,
                    onDismissRequest = { expandedStructure = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                ) {
                    structuresMap.forEach { structureItem ->
                        DropdownMenuItem(onClick = {
                            expandedStructure = false
                            structureEnable = structureItem.key == 0
                            structure = if (structureEnable) "" else structureItem.value
                            focusManager.clearFocus()
                            checkTextFields()
                        }) {
                            Text(text = structureItem.value)
                        }
                    }
                }
                LaunchedEffect(structureEnable) {
                    if (structureEnable) {
                        delay(200)
                        focusRequester.requestFocus()
                    }
                }
            }
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
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreenUI(
        MutableStateFlow(ResponseState.Idle),
        MutableStateFlow(ResponseState.Idle),
        {},
        {},
        {})
}