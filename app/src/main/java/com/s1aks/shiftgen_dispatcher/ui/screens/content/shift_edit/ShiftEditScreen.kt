package com.s1aks.shiftgen_dispatcher.ui.screens.content.shift_edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Action
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.data.entities.Periodicity
import com.s1aks.shiftgen_dispatcher.data.entities.Shift
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import com.s1aks.shiftgen_dispatcher.ui.elements.DoneIconButton
import com.s1aks.shiftgen_dispatcher.ui.elements.LoadingIndicator
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState
import com.s1aks.shiftgen_dispatcher.utils.fio
import com.s1aks.shiftgen_dispatcher.utils.getHours
import com.s1aks.shiftgen_dispatcher.utils.getMinutes
import com.s1aks.shiftgen_dispatcher.utils.getPeriodicity
import com.s1aks.shiftgen_dispatcher.utils.inc
import com.s1aks.shiftgen_dispatcher.utils.onSuccess
import com.s1aks.shiftgen_dispatcher.utils.setOutlinedTextFieldBorderColor
import com.s1aks.shiftgen_dispatcher.utils.setOutlinedTextFieldColorsWithThis
import com.s1aks.shiftgen_dispatcher.utils.setOutlinedTextFieldIconByExpanded
import com.s1aks.shiftgen_dispatcher.utils.timeToMillis
import com.s1aks.shiftgen_dispatcher.utils.toDateTimeString
import com.s1aks.shiftgen_dispatcher.utils.toDayString
import com.s1aks.shiftgen_dispatcher.utils.toLocalDateTime
import com.s1aks.shiftgen_dispatcher.utils.toTimeString
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.temporal.ChronoUnit

@Composable
fun ShiftEditScreen(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: ShiftEditViewModel,
    id: Int
) {
    val new = id == 0
    val screenState: ShiftEditScreenState by remember {
        mutableStateOf(ShiftEditScreenState())
    }
    var returnState: ShiftEditScreenReturnState by remember {
        mutableStateOf(ShiftEditScreenReturnState())
    }
    var directionsLoadingState by rememberSaveable { mutableStateOf(false) }
    val directionsState by viewModel.directionsState.collectAsState()
    directionsState.onSuccess(LocalContext.current, { directionsLoadingState = it }) {
        screenState.directions = (directionsState as ResponseState.Success<List<Direction>>).item
    }
    var workersLoadingState by rememberSaveable { mutableStateOf(false) }
    val workersState by viewModel.workersState.collectAsState()
    workersState.onSuccess(LocalContext.current, { workersLoadingState = it }) {
        screenState.workers = (workersState as ResponseState.Success<List<Worker>>).item
    }
    var shiftLoadingState by rememberSaveable { mutableStateOf(false) }
    val shiftState by viewModel.shiftState.collectAsState()
    shiftState.onSuccess(LocalContext.current, { shiftLoadingState = it }) {
        screenState.shiftData = (shiftState as ResponseState.Success<Shift>).item
    }
    var responseLoadingState by rememberSaveable { mutableStateOf(false) }
    val responseState by viewModel.responseState.collectAsState()
    responseState.onSuccess(LocalContext.current, { responseLoadingState = it }) {
        returnState.shiftData = null
        viewModel.clearStates()
        navController.popBackStack()
    }
    val loadingState by remember {
        derivedStateOf {
            (!new && screenState.shiftData == null)
                    || directionsLoadingState
                    || workersLoadingState
                    || shiftLoadingState
                    || responseLoadingState
        }
    }
    LaunchedEffect(Unit) {
        onComposing(
            MainScreenState(
                title = { Text(if (new) "Добавить смену" else "Редактировать смену") },
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = returnState.allFieldsOk) {
                        returnState.shiftData?.let {
                            if (new) {
                                viewModel.insertData(it)
                            } else {
                                viewModel.updateData(it)
                            }
                        }
                    }
                }
            )
        )
        viewModel.getBasicData()
        if (!new) {
            viewModel.getShiftData(id)
        }
    }
    if (loadingState) {
        LoadingIndicator()
    } else {
        ShiftEditScreenUI(screenState) { returnState = it }
    }
}

data class ShiftEditScreenState(
    var directions: List<Direction>? = null,
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
    val periodYearMonth by rememberSaveable { mutableStateOf(YearMonth.now().toString()) }
    val periodicityList: List<String> = Periodicity.values().map { it.label }
    var periodicity by rememberSaveable { mutableStateOf(Periodicity.SINGLE.label) }
    val workersList: List<String> =
        listOf("Автоматически")
    //.plus(screenState.workers?.map { it.fio() } ?: listOf())
    var worker by rememberSaveable { mutableStateOf(workersList[0]) }
    val directionsList: List<String> =
        if (worker == workersList[0]) {
            screenState.directions?.map { it.name } ?: listOf()
        } else {
            screenState.workers?.firstOrNull { it.fio() == worker }?.accessToDirections?.map { directionId ->
                screenState.directions?.first { it.id == directionId }?.name
                    ?: throw RuntimeException("Error worker directions id's. Directions not contain this.")
            } ?: listOf()
        }
    var direction by rememberSaveable(worker) { mutableStateOf("") }
    var startTime by rememberSaveable { mutableStateOf("") }
    var endTime by rememberSaveable { mutableStateOf("") }
    var restTime by rememberSaveable { mutableStateOf("") }

    val nameFieldOk = fun(): Boolean = name.length in 1..30
    val periodicityFieldOk = fun(): Boolean = periodicity.isNotBlank()
    val workerFieldOk = fun(): Boolean = worker.isNotBlank()
    val directionFieldOk = fun(): Boolean = direction.isNotBlank()
    val startTimeFieldOk = fun(): Boolean = startTime.isNotBlank()
    val endTimeFieldOk = fun(): Boolean = endTime.isNotBlank()
            && endTime.toLocalDateTime() > startTime.toLocalDateTime()
    val restTimeFieldOk = fun(): Boolean = restTime.isNotBlank()
    val allFieldsOk by remember {
        derivedStateOf {
            nameFieldOk()
                    && periodicityFieldOk()
                    && workerFieldOk()
                    && directionFieldOk()
                    && startTimeFieldOk()
                    && endTimeFieldOk()
                    && restTimeFieldOk()
        }
    }

    var dateStart by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var timeStart by rememberSaveable { mutableStateOf(LocalTime.now()) }
    var dateEnd by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var timeEnd by rememberSaveable { mutableStateOf(LocalTime.now()) }
    var timeRest by rememberSaveable { mutableStateOf(LocalTime.of(0, 0)) }

    if (screenState.shiftData?.id != id) {
        screenState.shiftData?.let { data ->
            id = data.id
            name = data.name
            periodicity = data.periodicity.label
            worker =
                screenState.workers?.firstOrNull { it.id == data.workerId }.fio() ?: workersList[0]
            direction = screenState.directions?.firstOrNull { it.id == data.directionId }?.name
                ?: throw RuntimeException("Shift not contains direction value.")
            startTime = data.startTime.run {
                dateStart = this.toLocalDate()
                timeStart = this.toLocalTime()
                "${toDayString()} ${toTimeString()}"
            }
            endTime = data.startTime.plus(data.duration, ChronoUnit.MILLIS).run {
                dateEnd = this.toLocalDate()
                timeEnd = this.toLocalTime()
                "${toDayString()} ${toTimeString()}"
            }
            restTime = data.restDuration.run {
                timeRest = LocalTime.of(this.getHours(), this.getMinutes())
                toTimeString()
            }
        }
    }
    returnState(
        ShiftEditScreenReturnState(
            allFieldsOk = allFieldsOk,
            shiftData = if (allFieldsOk) {
                val startDateTime = startTime.toLocalDateTime()
                val endDateTime = endTime.toLocalDateTime()
                val shiftDuration = Duration.between(startDateTime, endDateTime).toMillis()
                Shift(
                    id,
                    name,
                    YearMonth.parse(periodYearMonth),
                    periodicity.getPeriodicity(),
                    screenState.workers?.firstOrNull { it.fio() == worker }?.id,
                    screenState.directions?.firstOrNull { it.name == direction }?.id
                        ?: throw RuntimeException("Empty direction."),
                    Action.WORK,
                    startDateTime,
                    shiftDuration,
                    restTime.timeToMillis()
                )
            } else null
        )
    )
    var expandedPeriodicity by rememberSaveable { mutableStateOf(false) }
    var expandedWorker by rememberSaveable { mutableStateOf(false) }
    var expandedDirection by rememberSaveable { mutableStateOf(false) }
    val iconPeriodicity = expandedPeriodicity.setOutlinedTextFieldIconByExpanded()
    val iconWorker = expandedWorker.setOutlinedTextFieldIconByExpanded()
    val iconDirection = expandedDirection.setOutlinedTextFieldIconByExpanded()
    val periodicityColor = periodicity.setOutlinedTextFieldBorderColor()
    val workerColor = worker.setOutlinedTextFieldBorderColor()
    val directionColor = direction.setOutlinedTextFieldBorderColor()
    val startTimeColor = startTime.setOutlinedTextFieldBorderColor()
    val endTimeColor = endTime.setOutlinedTextFieldBorderColor()
    val restTimeColor = restTime.setOutlinedTextFieldBorderColor()
    val focusManager = LocalFocusManager.current
//    val focusRequester = remember { FocusRequester() }

    val context = LocalContext.current

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
            label = { Text(text = "Наименование") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
            )
        )
        Box {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = periodicity,
                singleLine = true,
                onValueChange = { periodicity = it },
                label = { Text(text = "Периодичность") },
                trailingIcon = {
                    Icon(
                        iconPeriodicity, "Периодичность",
                        Modifier.clickable { expandedPeriodicity++ },
                        tint = periodicityColor
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                enabled = false,
                colors = periodicityColor.setOutlinedTextFieldColorsWithThis()
            )
            DropdownMenu(
                expanded = expandedPeriodicity,
                onDismissRequest = { expandedPeriodicity = false }
            ) {
                periodicityList.forEach { name ->
                    DropdownMenuItem(
                        onClick = {
                            periodicity = name
                            expandedPeriodicity = false
                            focusManager.clearFocus()
                        }
                    ) { Text(text = name) }
                }
            }
        }
        Box {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = worker,
                singleLine = true,
                onValueChange = { worker = it },
                label = { Text(text = "Рабочий") },
                trailingIcon = {
                    Icon(
                        iconWorker, "Рабочий",
                        Modifier.clickable { expandedWorker++ },
                        tint = workerColor
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                enabled = false,
                colors = workerColor.setOutlinedTextFieldColorsWithThis()
            )
            DropdownMenu(
                expanded = expandedWorker,
                onDismissRequest = { expandedWorker = false }
            ) {
                workersList.forEach { name ->
                    DropdownMenuItem(
                        onClick = {
                            worker = name
                            expandedWorker = false
                            focusManager.clearFocus()
                        }
                    ) { Text(text = name) }
                }
            }
        }
        Box {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = direction,
                singleLine = true,
                onValueChange = { direction = it },
                label = { Text(text = "Направление") },
                trailingIcon = {
                    Icon(
                        iconDirection, "Направление",
                        Modifier.clickable { expandedDirection++ },
                        tint = directionColor
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                enabled = false,
                colors = directionColor.setOutlinedTextFieldColorsWithThis()
            )
            DropdownMenu(
                expanded = expandedDirection,
                onDismissRequest = { expandedDirection = false }
            ) {
                directionsList.forEach { name ->
                    DropdownMenuItem(
                        onClick = {
                            direction = name
                            expandedDirection = false
                            focusManager.clearFocus()
                        }
                    ) { Text(text = name) }
                }
            }
        }
        Box {
            val timePicker = TimePickerDialog(
                context,
                { _: TimePicker, mHours: Int, mMinutes: Int ->
                    timeStart = LocalTime.of(mHours, mMinutes)
                    startTime = LocalDateTime.of(dateStart, timeStart).toDateTimeString()
                }, timeStart.hour, timeStart.minute, true
            )
            val datePicker = DatePickerDialog(
                context,
                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                    dateStart = LocalDate.of(mYear, mMonth, mDayOfMonth)
                    timePicker.show()
                }, dateStart.year, dateStart.monthValue, dateStart.dayOfMonth
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = startTime,
                singleLine = true,
                onValueChange = { startTime = it },
                label = { Text(text = "Время начала") },
                trailingIcon = {
                    Icon(
                        iconDirection, "Время начала",
                        Modifier.clickable {
                            datePicker.show()
                        },
                        tint = startTimeColor
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                enabled = false,
                colors = startTimeColor.setOutlinedTextFieldColorsWithThis()
            )
        }
        Box {
            val timePicker = TimePickerDialog(
                context,
                { _: TimePicker, mHours: Int, mMinutes: Int ->
                    timeEnd = LocalTime.of(mHours, mMinutes)
                    endTime = LocalDateTime.of(dateEnd, timeEnd).toDateTimeString()
                }, timeEnd.hour, timeEnd.minute, true
            )
            val datePicker = DatePickerDialog(
                context,
                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                    dateEnd = LocalDate.of(mYear, mMonth, mDayOfMonth)
                    timePicker.show()
                }, dateEnd.year, dateEnd.monthValue, dateEnd.dayOfMonth
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = endTime,
                singleLine = true,
                onValueChange = { endTime = it },
                isError = !endTimeFieldOk(),
                label = { Text(text = "Время окончания") },
                trailingIcon = {
                    Icon(
                        iconDirection, "Время окончания",
                        Modifier.clickable {
                            datePicker.show()
                        },
                        tint = endTimeColor
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                enabled = false,
                colors = endTimeColor.setOutlinedTextFieldColorsWithThis()
            )
        }
        Box {
            val timePicker = TimePickerDialog(
                context,
                { _: TimePicker, mHours: Int, mMinutes: Int ->
                    timeRest = LocalTime.of(mHours, mMinutes)
                    restTime = timeRest.toTimeString()
                }, timeRest.hour, timeRest.minute, true
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = restTime,
                singleLine = true,
                onValueChange = { restTime = it },
                label = { Text(text = "Время отдыха") },
                trailingIcon = {
                    Icon(
                        iconDirection, "Время отдыха",
                        Modifier.clickable {
                            timePicker.show()
                        },
                        tint = restTimeColor
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                enabled = false,
                colors = restTimeColor.setOutlinedTextFieldColorsWithThis()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShiftEditScreen() {
    ShiftEditScreenUI()
}