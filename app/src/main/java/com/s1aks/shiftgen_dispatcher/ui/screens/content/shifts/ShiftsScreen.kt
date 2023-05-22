package com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Shift
import com.s1aks.shiftgen_dispatcher.utils.toastError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ShiftsScreen(navController: NavHostController, viewModel: ShiftsViewModel) {
    ShiftsScreenUI(viewModel.shiftsState)
}

@Composable
fun ShiftsScreenUI(
    responseStateFlow: StateFlow<ResponseState<List<Shift>>> = MutableStateFlow(ResponseState.Idle)
) {
    var loadingState by rememberSaveable { mutableStateOf(false) }
    val responseState by responseStateFlow.collectAsState()
    when (responseState) {
        is ResponseState.Idle -> {
            loadingState = false
        }

        is ResponseState.Loading -> {
            loadingState = true
        }

        is ResponseState.Success -> {

        }

        is ResponseState.Error -> {
            (responseState as ResponseState.Error).toastError(context = LocalContext.current)
        }
    }
    if (loadingState) {
        CircularProgressIndicator()
    } else {
        Text(text = "Shifts")
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewShiftsScreen() {
    ShiftsScreenUI()
}