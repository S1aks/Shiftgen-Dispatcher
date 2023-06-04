package com.s1aks.shiftgen_dispatcher.ui.screens.content.structure

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme.colors
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Structure
import com.s1aks.shiftgen_dispatcher.ui.elements.DoneIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState
import com.s1aks.shiftgen_dispatcher.utils.onSuccess

@Composable
fun StructureScreen(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: StructureViewModel
) {
    var screenState: StructureScreenState by remember {
        mutableStateOf(StructureScreenState(allFieldsOk = false, structureData = null))
    }
    var loadingState by rememberSaveable { mutableStateOf(false) }
    val structureState by viewModel.structureState.collectAsState()
    structureState.onSuccess(LocalContext.current, { loadingState = it }) {
        screenState.structureData = (structureState as ResponseState.Success<Structure>).item
    }
    val responseState by viewModel.responseState.collectAsState()
    responseState.onSuccess(LocalContext.current, { loadingState = it }) {
        screenState.structureData = null
        viewModel.clearStates()
        navController.popBackStack()
    }
    LaunchedEffect(Unit) {
        onComposing(
            MainScreenState(
                title = "Редактировать структуру",
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = screenState.allFieldsOk) {
                        screenState.structureData?.let { viewModel.saveData(it) }
                    }
                }
            )
        )
    }
    if (loadingState || screenState.structureData == null) {
        CircularProgressIndicator()
    } else {
        StructureScreenUI(screenState) { screenState = it }
    }
}

data class StructureScreenState(
    val allFieldsOk: Boolean,
    var structureData: Structure?
)

@Composable
fun StructureScreenUI(
    screenState: StructureScreenState = StructureScreenState(false, null),
    returnState: (StructureScreenState) -> Unit = {}
) {
    var id by rememberSaveable { mutableStateOf(0) }
    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var restHours by rememberSaveable { mutableStateOf("") }
    var allowedConsecutiveNights by rememberSaveable { mutableStateOf("") }
    var nightStartHour by rememberSaveable { mutableStateOf("") }
    var nightEndHour by rememberSaveable { mutableStateOf("") }
    var dispatcherPin by rememberSaveable { mutableStateOf("") }
    val nameFieldOk = fun(): Boolean = name.length in 4..25
    val descriptionFieldOk = fun(): Boolean = description.length < 256
    val restHoursFieldOk = fun(): Boolean = restHours.toIntOrNull() in 0..48
    val allowedConsecutiveNightsFieldOk =
        fun(): Boolean = allowedConsecutiveNights.toIntOrNull() in 0..10
    val nightStartHourFieldOk = fun(): Boolean = nightStartHour.toIntOrNull() in 0..23
    val nightEndHourFieldOk = fun(): Boolean = nightEndHour.toIntOrNull() in 0..23
    val allFieldsOk by remember {
        derivedStateOf {
            nameFieldOk()
                    && descriptionFieldOk()
                    && restHoursFieldOk()
                    && allowedConsecutiveNightsFieldOk()
                    && nightStartHourFieldOk()
                    && nightEndHourFieldOk()
        }
    }
    if (screenState.structureData?.id != id) {
        screenState.structureData?.let { data ->
            id = data.id
            name = data.name
            description = data.description ?: ""
            restHours = data.restHours.toString()
            allowedConsecutiveNights = data.allowedConsecutiveNights.toString()
            nightStartHour = data.nightStartHour.toString()
            nightEndHour = data.nightEndHour.toString()
            dispatcherPin = data.dispatcherPin
        }
    }
    returnState(
        StructureScreenState(
            allFieldsOk = allFieldsOk,
            structureData = if (allFieldsOk) {
                Structure(
                    id,
                    name,
                    description,
                    restHours.toInt(),
                    allowedConsecutiveNights.toInt(),
                    nightStartHour.toInt(),
                    nightEndHour.toInt(),
                    dispatcherPin
                )
            } else null
        )
    )
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { description = it },
            isError = !descriptionFieldOk(),
            label = { Text(text = "Описание") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = restHours,
            singleLine = true,
            onValueChange = { restHours = it },
            isError = !restHoursFieldOk(),
            label = { Text(text = "Часов отдыха после смены") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = allowedConsecutiveNights,
            singleLine = true,
            onValueChange = { allowedConsecutiveNights = it },
            isError = !allowedConsecutiveNightsFieldOk(),
            label = { Text(text = "Разрешенных ночей подряд") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = nightStartHour,
            singleLine = true,
            onValueChange = { nightStartHour = it },
            isError = !nightStartHourFieldOk(),
            label = { Text(text = "Час начала ночной смены") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = nightEndHour,
            singleLine = true,
            onValueChange = { nightEndHour = it },
            isError = !nightEndHourFieldOk(),
            label = { Text(text = "Час окончания ночной смены") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            )
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Пин диспетчера: $dispatcherPin",
            color = colors.primary,
            fontSize = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStructureScreenUI() {
    StructureScreenUI()
}
