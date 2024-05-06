package com.s1aks.shiftgen_dispatcher.ui.screens.content.worker_edit

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import com.s1aks.shiftgen_dispatcher.ui.elements.DoneIconButton
import com.s1aks.shiftgen_dispatcher.ui.elements.LoadingIndicator
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState
import com.s1aks.shiftgen_dispatcher.ui.screens.content.direction_edit.DirectionAccessItem
import com.s1aks.shiftgen_dispatcher.utils.onSuccess

@Composable
fun WorkerEditScreen(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: WorkerEditViewModel,
    id: Int
) {
    val new = id == 0
    var screenState: WorkerEditScreenState by remember {
        mutableStateOf(
            WorkerEditScreenState(
                allFieldsOk = false,
                workerData = null
            )
        )
    }
    var loadingState by rememberSaveable { mutableStateOf(false) }
    val workerState by viewModel.workerState.collectAsState()
    workerState.onSuccess(LocalContext.current, { loadingState = it }) {
        screenState.workerData = (workerState as ResponseState.Success<Worker>).item
    }
    var directions by rememberSaveable { mutableStateOf<List<Direction>>(listOf()) }
    val directionsState by viewModel.directionsState.collectAsState()
    directionsState.onSuccess(LocalContext.current, { loadingState = it }) {
        directions = (directionsState as ResponseState.Success<List<Direction>>).item
    }
    val responseState by viewModel.responseState.collectAsState()
    responseState.onSuccess(LocalContext.current, { loadingState = it }) {
        screenState.workerData = null
        viewModel.clearStates()
        navController.popBackStack()
    }
    LaunchedEffect(Unit) {
        onComposing(
            MainScreenState(
                title = { Text(if (new) "Добавить рабочего" else "Редактировать рабочего") },
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = screenState.allFieldsOk) {
                        screenState.workerData?.let {
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
        if (!new) {
            viewModel.getData(id)
        }
    }
    if (loadingState || (!new && screenState.workerData == null)) {
        LoadingIndicator()
    } else {
        WorkerEditScreenUI(directions, screenState) { screenState = it }
    }
}

data class WorkerEditScreenState(
    val allFieldsOk: Boolean,
    var workerData: Worker?
)

@Composable
fun WorkerEditScreenUI(
    directions: List<Direction> = listOf(Direction(1, "DDDD"), Direction(2, "GGGG")),
    screenState: WorkerEditScreenState = WorkerEditScreenState(false, null),
    returnState: (WorkerEditScreenState) -> Unit = {}
) {
    var id by rememberSaveable { mutableStateOf(0) }
    var personnelNumber by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var patronymic by rememberSaveable { mutableStateOf("") }
    var accessList by rememberSaveable { mutableStateOf(listOf<Int>()) }
    val listState: LazyListState = rememberLazyListState()
    val personnelNumberFieldOk = fun(): Boolean = personnelNumber.length in 1..30
    val firstNameFieldOk = fun(): Boolean = firstName.length in 1..30
    val lastNameFieldOk = fun(): Boolean = lastName.length in 1..30
    val patronymicFieldOk = fun(): Boolean = patronymic.length in 1..30
    val allFieldsOk by remember {
        derivedStateOf {
            personnelNumberFieldOk()
                    && firstNameFieldOk()
                    && lastNameFieldOk()
                    && patronymicFieldOk()
        }
    }
    if (screenState.workerData?.id != id) {
        screenState.workerData?.let { data ->
            id = data.id
            personnelNumber = data.personnelNumber.toString()
            firstName = data.firstName
            lastName = data.lastName
            patronymic = data.patronymic ?: ""
            accessList = data.accessToDirections ?: listOf()
        }
    }
    returnState(
        WorkerEditScreenState(
            allFieldsOk = allFieldsOk,
            workerData = if (allFieldsOk) {
                Worker(
                    id,
                    personnelNumber.toInt(),
                    userId = null,
                    firstName,
                    lastName,
                    patronymic.ifBlank { null },
                    accessList
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
            value = personnelNumber,
            singleLine = true,
            onValueChange = { personnelNumber = it },
            isError = !personnelNumberFieldOk(),
            label = { Text(text = "Табельный номер") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = lastName,
            singleLine = true,
            onValueChange = { lastName = it },
            isError = !lastNameFieldOk(),
            label = { Text(text = "Фамилия") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = firstName,
            singleLine = true,
            onValueChange = { firstName = it },
            isError = !firstNameFieldOk(),
            label = { Text(text = "Имя") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = patronymic,
            singleLine = true,
            onValueChange = { patronymic = it },
            isError = !patronymicFieldOk(),
            label = { Text(text = "Отчество") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
            )
        )
        Text(
            modifier = Modifier.padding(start = 5.dp, top = 12.dp),
            fontSize = 16.sp,
            text = "Допуск к направлениям"
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 7.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled),
                    shape = RoundedCornerShape(4.dp)
                ),
            state = listState
        ) {
            items(directions) { direction ->
                val checked = accessList.contains(direction.id)
                DirectionAccessItem(direction.name, checked) {
                    accessList = if (checked) {
                        accessList.filter { it != direction.id }
                    } else {
                        val list = accessList.toMutableList()
                        list.add(direction.id)
                        list
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWorkerEditScreen() {
    WorkerEditScreenUI()
}