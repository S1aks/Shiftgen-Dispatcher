package com.s1aks.shiftgen_dispatcher.ui.screens.content.structure

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Structure
import com.s1aks.shiftgen_dispatcher.ui.elements.DoneIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.AppBarState
import com.s1aks.shiftgen_dispatcher.utils.toastError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun StructureScreen(
    navController: NavHostController,
    onComposing: (AppBarState) -> Unit,
    viewModel: StructureViewModel
) {
    var buttonDoneEnable by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = "Редактировать структуру",
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = false) {
                        navController.popBackStack()
                    }
                }
            )
        )
    }
    StructureScreenUI(viewModel.structureState) { buttonDoneEnable = it }
}

@Composable
fun StructureScreenUI(
    responseStateFlow: StateFlow<ResponseState<Structure>> = MutableStateFlow(ResponseState.Idle),
    buttonEnable: (Boolean) -> Unit = {}
) {
    var loadingState by rememberSaveable { mutableStateOf(false) }
    val responseState by responseStateFlow.collectAsState()

    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var restHours by rememberSaveable { mutableStateOf(0) }
    var allowedConsecutiveNights by rememberSaveable { mutableStateOf(0) }
    var nightStartHour by rememberSaveable { mutableStateOf(0) }
    var nightEndHour by rememberSaveable { mutableStateOf(6) }
    val nameFieldOk = fun(): Boolean = name.length in 4..25
    val descriptionFieldOk = fun(): Boolean = description.length < 256
    val restHoursFieldOk = fun(): Boolean = restHours in 0..48
    val allowedConsecutiveNightsFieldOk = fun(): Boolean = allowedConsecutiveNights in 0..10
    val nightStartHourFieldOk = fun(): Boolean = nightStartHour in 0..23
    val nightEndHourFieldOk = fun(): Boolean = nightEndHour in 0..23
    val buttonDoneEnable by remember {
        derivedStateOf {
            nameFieldOk()
                    && descriptionFieldOk()
                    && restHoursFieldOk()
                    && allowedConsecutiveNightsFieldOk()
                    && nightStartHourFieldOk()
                    && nightEndHourFieldOk()
        }
    }
    buttonEnable(buttonDoneEnable)
    when (responseState) {
        is ResponseState.Idle -> {
            loadingState = false
        }

        is ResponseState.Loading -> {
            loadingState = true
        }

        is ResponseState.Success -> {
            val structure = (responseState as ResponseState.Success<Structure>).item
            name = structure.name
            description = structure.description ?: ""
            restHours = structure.restHours
            allowedConsecutiveNights = structure.allowedConsecutiveNights
            nightStartHour = structure.nightStartHour
            nightEndHour = structure.nightEndHour
        }

        is ResponseState.Error -> {
            (responseState as ResponseState.Error).toastError(context = LocalContext.current)
        }
    }
    if (loadingState) {
        CircularProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = name,
                singleLine = true,
                onValueChange = { name = it },
                isError = !nameFieldOk(),
                label = { Text(text = "Название") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
                )
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                isError = !descriptionFieldOk(),
                label = { Text(text = "Описание") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
                )
            )
            OutlinedTextField(
                value = restHours.toString(),
                singleLine = true,
                onValueChange = { restHours = it.toInt() },
                isError = !restHoursFieldOk(),
                label = { Text(text = "Часов отдыха после смены") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                )
            )
            OutlinedTextField(
                value = allowedConsecutiveNights.toString(),
                singleLine = true,
                onValueChange = { allowedConsecutiveNights = it.toInt() },
                isError = !allowedConsecutiveNightsFieldOk(),
                label = { Text(text = "Разрешенных ночей подряд") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                )
            )
            OutlinedTextField(
                value = nightStartHour.toString(),
                singleLine = true,
                onValueChange = { nightStartHour = it.toInt() },
                isError = !nightStartHourFieldOk(),
                label = { Text(text = "Час начала ночной смены") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                )
            )
            OutlinedTextField(
                value = nightEndHour.toString(),
                singleLine = true,
                onValueChange = { nightEndHour = it.toInt() },
                isError = !nightEndHourFieldOk(),
                label = { Text(text = "Час окончания ночной смены") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStructureScreenUI() {
    StructureScreenUI()
}
