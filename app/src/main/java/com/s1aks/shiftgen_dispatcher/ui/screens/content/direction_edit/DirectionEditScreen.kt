package com.s1aks.shiftgen_dispatcher.ui.screens.content.direction_edit

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.ui.elements.DoneIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState
import com.s1aks.shiftgen_dispatcher.utils.onSuccess

@Composable
fun DirectionEditScreen(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: DirectionEditViewModel
) {
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
                title = "Добавить направление",
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = screenState.allFieldsOk) {
                        screenState.directionData?.let { viewModel.updateData(it) }
                    }
                }
            )
        )
    }
    if (loadingState) { // || screenState...Data == null
        CircularProgressIndicator()
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

}