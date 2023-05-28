package com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.models.ShiftModel
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.elements.AddIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.AppBarState
import com.s1aks.shiftgen_dispatcher.utils.toastError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ShiftsScreen(
    navController: NavHostController,
    onComposing: (AppBarState) -> Unit,
    viewModel: ShiftsViewModel
) {
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = "Смены",
                drawerEnabled = true,
                actions = {
                    AddIconButton { navController.navigate(Screen.ShiftAdd.route) }
                }
            )
        )
    }
    ShiftsScreenUI(viewModel.shiftsState)
}

private val testShift = ShiftModel(
    "25.05.2023",
    "20:00",
    "Рейс 512",
    "Москва - Ростов",
    "Михайлов Н.А.",
    "14:25"
)

@Preview(showBackground = true)
@Composable
fun ShiftsItem(
    shift: ShiftModel = testShift   // todo REMOVE test
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        val (date, time, shift_name, worker, work_time) = createRefs()
        val startGuideline = createGuidelineFromStart(0.25f)
        val endGuideline = createGuidelineFromStart(0.85f)
        Text(text = shift.start_date,
            modifier = Modifier
                .constrainAs(date) {
                    start.linkTo(parent.start)
                    end.linkTo(startGuideline)
                    top.linkTo(parent.top)
                    bottom.linkTo(time.top)
                }
        )
        Text(text = shift.start_time,
            modifier = Modifier
                .constrainAs(time) {
                    start.linkTo(parent.start)
                    end.linkTo(startGuideline)
                    top.linkTo(date.bottom)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(text = "${shift.name} ${shift.direction}",
            modifier = Modifier
                .constrainAs(shift_name) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(parent.top)
                    bottom.linkTo(worker.top)
                }
        )
        Text(text = shift.worker,
            modifier = Modifier
                .constrainAs(worker) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(shift_name.bottom)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(text = shift.work_time,
            modifier = Modifier
                .constrainAs(work_time) {
                    start.linkTo(endGuideline)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
    Divider(color = colors.primaryVariant, thickness = 1.dp)
}

@Composable
fun ShiftsScreenUI(
    responseStateFlow: StateFlow<ResponseState<List<ShiftModel>>> = MutableStateFlow(ResponseState.Idle)
) {
    var loadingState by rememberSaveable { mutableStateOf(false) }
    val responseState by responseStateFlow.collectAsState()
    var shifts by remember { mutableStateOf(listOf<ShiftModel>()) }
    when (responseState) {
        is ResponseState.Idle -> {
            loadingState = false
        }

        is ResponseState.Loading -> {
            loadingState = true
        }

        is ResponseState.Success -> {
            shifts = (responseState as ResponseState.Success).item
        }

        is ResponseState.Error -> {
            (responseState as ResponseState.Error).toastError(context = LocalContext.current)
        }
    }
    if (loadingState) {
        CircularProgressIndicator()
    } else {
        LazyColumn {
            items(shifts) { shift ->
                ShiftsItem(shift)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShiftsScreen() {
    ShiftsScreenUI()
}