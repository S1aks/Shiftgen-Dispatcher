package com.s1aks.shiftgen_dispatcher.ui.screens.content.shift_edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.s1aks.shiftgen_dispatcher.utils.inc
import com.s1aks.shiftgen_dispatcher.utils.onSuccess
import com.s1aks.shiftgen_dispatcher.utils.setOutlinedTextFieldBorderColor
import com.s1aks.shiftgen_dispatcher.utils.setOutlinedTextFieldColorsWithThis
import com.s1aks.shiftgen_dispatcher.utils.setOutlinedTextFieldIconByExpanded
import com.s1aks.shiftgen_dispatcher.utils.toDay
import com.s1aks.shiftgen_dispatcher.utils.toLocalDateTime
import com.s1aks.shiftgen_dispatcher.utils.toTime
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

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
    var timeBlocksLoadingState by rememberSaveable { mutableStateOf(false) }
    val timeBlocksState by viewModel.timeBlocksState.collectAsState()
    timeBlocksState.onSuccess(LocalContext.current, { timeBlocksLoadingState = it }) {
        screenState.timeBlocks = (timeBlocksState as ResponseState.Success<List<TimeBlock>>).item
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
    val loadingState by remember {
        derivedStateOf {
            directionsLoadingState
                    && timeBlocksLoadingState
                    && workersLoadingState
                    && (shiftLoadingState || new)
        }
    }
    LaunchedEffect(Unit) {
        onComposing(
            MainScreenState(
                title = "Добавить смену",
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = returnState.allFieldsOk) {
                        navController.popBackStack()
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
    var timeBlocks: List<TimeBlock>? = null,
    var workers: List<Worker>? = null,
    var shiftData: Shift? = null
)

data class ShiftEditScreenReturnState(
    val allFieldsOk: Boolean = false,
    var shiftData: Shift? = null
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShiftEditScreenUI(
    screenState: ShiftEditScreenState = ShiftEditScreenState(),
    returnState: (ShiftEditScreenReturnState) -> Unit = {}
) {
    var id by rememberSaveable { mutableStateOf(0) }
    var name by rememberSaveable { mutableStateOf("") }
    val periodYearMonth by rememberSaveable { mutableStateOf("") }
    var periodicity by rememberSaveable { mutableStateOf(Periodicity.SINGLE.label) }
    var worker by rememberSaveable { mutableStateOf("Автоматически") }
    var direction by rememberSaveable(worker) { mutableStateOf("") }
    var startTime by rememberSaveable { mutableStateOf("") }
    var timeBlocksIds by rememberSaveable { mutableStateOf(listOf<Int>()) }
    val periodicityList: List<String> = Periodicity.values().map { it.label }
    val workersList: List<String> =
        listOf("Автоматически")
    //.plus(screenState.workers?.map { it.fio() } ?: listOf())
    val directionsList: List<String> =
        if (worker == "Автоматически") {
            screenState.directions?.map { it.name } ?: listOf()
        } else {
            screenState.workers?.first { it.fio() == worker }?.accessToDirections?.map { directionId ->
                screenState.directions?.first { it.id == directionId }?.name
                    ?: throw RuntimeException("Error worker directions id's. Directions not contain this.")
            } ?: listOf()
        }
    val nameFieldOk = fun(): Boolean = name.length in 1..30
    val periodicityFieldOk = fun(): Boolean = periodicity.isNotBlank()
    val workerFieldOk = fun(): Boolean = worker.isNotBlank()
    val directionFieldOk = fun(): Boolean = direction.isNotBlank()
    val startTimeFieldOk = fun(): Boolean = startTime.isNotBlank()
    val timeBlocksFieldOk = fun(): Boolean = timeBlocksIds.isNotEmpty()
    val allFieldsOk by remember {
        derivedStateOf {
            nameFieldOk()
                    && periodicityFieldOk()
                    && workerFieldOk()
                    && directionFieldOk()
                    && startTimeFieldOk()
                    && timeBlocksFieldOk()
        }
    }
    if (screenState.shiftData?.id != id) {
        screenState.shiftData?.let { data ->
            id = data.id
            name = data.name
            periodicity = data.periodicity.label
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
    val focusManager = LocalFocusManager.current
//    val focusRequester = remember { FocusRequester() }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val showModalSheet = rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = { BottomSheetContent() }
    ) {
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
                val context = LocalContext.current
                var date = LocalDate.now()
                var time = LocalTime.now()
                val timePicker = TimePickerDialog(
                    context,
                    { _: TimePicker, mHours: Int, mMinutes: Int ->
                        time = LocalTime.of(mHours, mMinutes)
                        val dateTime = LocalDateTime.of(date, time)
                        startTime = dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"))
                    }, time.hour, time.minute, true
                )
                val datePicker = DatePickerDialog(
                    context,
                    { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                        date = LocalDate.of(mYear, mMonth, mDayOfMonth)
                        timePicker.show()
                    }, date.year, date.monthValue, date.dayOfMonth
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
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 6.dp, bottom = 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Список временных блоков смены:")
                Spacer(modifier = Modifier.weight(1.0f))
                Icon(
                    Icons.Filled.AddBox, "Переместить вверх",
                    Modifier
                        .clickable {
                            showModalSheet.value = !showModalSheet.value
                            scope.launch {
                                bottomSheetState.show()
                            }
                        }
                        .height(36.dp)
                        .width(36.dp),
                    tint = Color.Green
                )
            }
            LazyColumn(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled),
                        shape = RoundedCornerShape(5.dp)
                    )
            ) {
                items(listOf(testTimeBlock, testTimeBlock)) { timeBlock ->
                    TimeBlocksItem(timeBlock)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShiftEditScreen() {
    ShiftEditScreenUI()
}