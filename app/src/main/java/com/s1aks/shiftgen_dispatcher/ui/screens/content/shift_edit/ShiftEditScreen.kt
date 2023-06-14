package com.s1aks.shiftgen_dispatcher.ui.screens.content.shift_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.data.entities.Periodicity
import com.s1aks.shiftgen_dispatcher.data.entities.Shift
import com.s1aks.shiftgen_dispatcher.data.entities.TimeBlock
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import com.s1aks.shiftgen_dispatcher.ui.elements.DoneIconButton
import com.s1aks.shiftgen_dispatcher.ui.elements.LoadingIndicator
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState
import com.s1aks.shiftgen_dispatcher.utils.fio
import com.s1aks.shiftgen_dispatcher.utils.onSuccess
import com.s1aks.shiftgen_dispatcher.utils.toDay
import com.s1aks.shiftgen_dispatcher.utils.toLocalDateTime
import com.s1aks.shiftgen_dispatcher.utils.toTime
import java.time.YearMonth

@Composable
fun ShiftEditScreen(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: ShiftEditViewModel,
    id: Int
) {
    val new = id == 0
    var screenState: ShiftEditScreenState by remember {
        mutableStateOf(ShiftEditScreenState())
    }
    var returnState: ShiftEditScreenReturnState by remember {
        mutableStateOf(ShiftEditScreenReturnState())
    }
    var loadingState by rememberSaveable { mutableStateOf(false) }
    val shiftState by viewModel.shiftState.collectAsState()
    shiftState.onSuccess(LocalContext.current, { loadingState = it }) {
        screenState.shiftData = (shiftState as ResponseState.Success<Shift>).item
    }
    LaunchedEffect(Unit) {
        onComposing(
            MainScreenState(
                title = "Добавить смену",
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = false) {
                        navController.popBackStack()
                    }
                }
            )
        )
        if (!new) {
            viewModel.getData(id)
        }
    }
    if (loadingState || (!new && screenState.shiftData == null)) {
        LoadingIndicator()
    } else {
        ShiftEditScreenUI(screenState) { returnState = it }
    }
}

data class ShiftEditScreenState(
    var directions: List<Direction>? = null,
    var timeBlocks: List<TimeBlock>? = null,
    var workers: List<Worker>? = null,
    var shiftData: Shift? = null
)

data class ShiftEditScreenReturnState(
    val allFieldsOk: Boolean = false,
    var shiftData: Shift? = null
)

@Composable
fun ShiftEditScreenUI(
    screenState: ShiftEditScreenState = ShiftEditScreenState(),
    returnState: (ShiftEditScreenReturnState) -> Unit = {}
) {
    var id by rememberSaveable { mutableStateOf(0) }
    var name by rememberSaveable { mutableStateOf("") }
    var periodYearMonth by rememberSaveable { mutableStateOf("") }
    var periodicity by rememberSaveable { mutableStateOf(Periodicity.SINGLE.name) }
    var worker by rememberSaveable { mutableStateOf("") }
    var direction by rememberSaveable { mutableStateOf("") }
    var startTime by rememberSaveable { mutableStateOf("") }
    var timeBlocksIds by rememberSaveable { mutableStateOf(listOf<Int>()) }
    val nameFieldOk = fun(): Boolean = name.length in 1..30
    val allFieldsOk by remember {
        derivedStateOf {
            nameFieldOk()
        }
    }
    if (screenState.shiftData?.id != id) {
        screenState.shiftData?.let { data ->
            id = data.id
            name = data.name
            periodicity = data.periodicity.name
            worker = screenState.workers?.first { it.id == data.workerId }.fio()
            direction = screenState.directions?.first { it.id == data.directionId }?.name
                ?: throw RuntimeException("Shift not contains direction value.")
            startTime = data.startTime.run { "${toDay()} ${toTime()}" }
            timeBlocksIds = data.timeBlocksIds
        }
    }
    returnState(
        ShiftEditScreenReturnState(
            allFieldsOk = allFieldsOk,
            shiftData = if (allFieldsOk) {
                Shift(
                    id,
                    name,
                    YearMonth.parse(periodYearMonth),
                    Periodicity.valueOf(periodicity),
                    screenState.workers?.first { it.fio() == worker }?.id,
                    screenState.directions?.first { it.name == direction }?.id
                        ?: throw RuntimeException("Empty direction."),
                    startTime.toLocalDateTime(),
                    timeBlocksIds
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
            label = { Text(text = "Фамилия") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
            )
        )
    }
}