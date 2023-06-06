package com.s1aks.shiftgen_dispatcher.ui.screens.content.direction_edit

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
import com.s1aks.shiftgen_dispatcher.ui.elements.DoneIconButton
import com.s1aks.shiftgen_dispatcher.ui.elements.LoadingIndicator
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState
import com.s1aks.shiftgen_dispatcher.utils.logd
import com.s1aks.shiftgen_dispatcher.utils.onSuccess

@Composable
fun DirectionEditScreen(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: DirectionEditViewModel,
    id: Int
) {
    val new = id.logd() == 0
    var screenState: DirectionEditScreenState by remember {
        mutableStateOf(DirectionEditScreenState(allFieldsOk = false, directionData = null))
    }
    var loadingState by rememberSaveable { mutableStateOf(false) }
    val directionState by viewModel.directionState.collectAsState()
    directionState.onSuccess(LocalContext.current, { loadingState = it }) {
        screenState.directionData = (directionState as ResponseState.Success<Direction>).item
    }
    val responseState by viewModel.responseState.collectAsState()
    responseState.onSuccess(LocalContext.current, { loadingState = it }) {
        screenState.directionData = null
        viewModel.clearStates()
        navController.popBackStack()
    }
    LaunchedEffect(Unit) {
        onComposing(
            MainScreenState(
                title = if (new) "Добавить направление" else "Редактировать направление",
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = screenState.allFieldsOk) {
                        screenState.directionData?.let {
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
    if (loadingState || (!new && screenState.directionData == null)) {
        LoadingIndicator()
    } else {
        DirectionEditScreenUI(screenState) { screenState = it }
    }
}

data class DirectionEditScreenState(
    val allFieldsOk: Boolean,
    var directionData: Direction?
)

@Composable
fun DirectionEditScreenUI(
    screenState: DirectionEditScreenState = DirectionEditScreenState(false, null),
    returnState: (DirectionEditScreenState) -> Unit = {}
) {
    var id by rememberSaveable { mutableStateOf(0) }
    var name by rememberSaveable { mutableStateOf("") }
    val nameFieldOk = fun(): Boolean = name.length in 4..30
    val allFieldsOk by remember {
        derivedStateOf {
            nameFieldOk()
        }
    }
    if (screenState.directionData?.id != id) {
        screenState.directionData?.let { data ->
            id = data.id
            name = data.name
        }
    }
    returnState(
        DirectionEditScreenState(
            allFieldsOk = allFieldsOk,
            directionData = if (allFieldsOk) {
                Direction(id, name)
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
    }
}