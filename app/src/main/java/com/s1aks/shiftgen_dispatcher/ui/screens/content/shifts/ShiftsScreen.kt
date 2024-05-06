package com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.domain.models.ShiftItemModel
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.elements.AddIconButton
import com.s1aks.shiftgen_dispatcher.ui.elements.ContextMenuItem
import com.s1aks.shiftgen_dispatcher.ui.elements.LoadingIndicator
import com.s1aks.shiftgen_dispatcher.ui.elements.NextIconButton
import com.s1aks.shiftgen_dispatcher.ui.elements.PrevIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState
import com.s1aks.shiftgen_dispatcher.utils.dec
import com.s1aks.shiftgen_dispatcher.utils.inc
import com.s1aks.shiftgen_dispatcher.utils.onSuccess
import java.time.YearMonth

@Composable
fun ShiftsScreen(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: ShiftsViewModel
) {
    var yearMonth by rememberSaveable { mutableStateOf(YearMonth.now()) }
    var yearMonths by rememberSaveable { mutableStateOf(listOf<String>(yearMonth.toString())) }
    val screenState: ShiftsScreenState by remember {
        mutableStateOf(
            ShiftsScreenState(
                shifts = listOf(),
                contextMenu = listOf(
                    ContextMenuItem("Редактировать") { id ->
                        viewModel.clearStates()
                        navController.navigate(Screen.ShiftEdit(id.toString()).route)
                    },
                    ContextMenuItem("Удалить") { id ->
                        viewModel.deleteData(id, yearMonth)
                    }
                )
            )
        )
    }
    var yearMonthsLoadingState by rememberSaveable { mutableStateOf(false) }
    val yearMonthsState by viewModel.yearMonthsState.collectAsState()
    yearMonthsState.onSuccess(LocalContext.current, { yearMonthsLoadingState = it }) {
        yearMonths = (yearMonthsState as ResponseState.Success).item
    }
    var shiftsLoadingState by rememberSaveable { mutableStateOf(false) }
    val shiftsState by viewModel.shiftsState.collectAsState()
    shiftsState.onSuccess(LocalContext.current, { shiftsLoadingState = it }) {
        screenState.shifts = (shiftsState as ResponseState.Success).item
    }
    val loadingState by remember {
        derivedStateOf {
            yearMonthsLoadingState
                    || shiftsLoadingState
        }
    }
    LaunchedEffect(yearMonth) {
        onComposing(
            MainScreenState(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val prevButtonEnabled = if (yearMonths.isEmpty()) {
                            false
                        } else {
                            yearMonths.contains(yearMonth.minusMonths(1).toString())
                        }
                        val nextButtonEnabled = if (yearMonths.isEmpty()) {
                            false
                        } else {
                            yearMonths.contains(yearMonth.plusMonths(1).toString())
                        }
                        Text(
                            modifier = Modifier.padding(end = 8.dp),
                            text = "Смены"
                        )
                        PrevIconButton(enabled = prevButtonEnabled) { yearMonth-- }
                        Text(yearMonth.toString())
                        NextIconButton(enabled = nextButtonEnabled) { yearMonth++ }
                    }
                },
                drawerEnabled = true,
                actions = {
                    AddIconButton {
                        viewModel.clearStates()
                        navController.navigate(Screen.ShiftEdit("0").route)
                    }
                }
            )
        )
        screenState.shifts = listOf()
        viewModel.getData(yearMonth)
    }
    if (loadingState || yearMonths.isEmpty()) {
        LoadingIndicator()
    } else {
        ShiftsScreenUI(screenState)
    }
}

data class ShiftsScreenState(
    var shifts: List<ShiftItemModel>,
    val contextMenu: List<ContextMenuItem>
)


@Composable
fun ShiftsScreenUI(
    screenState: ShiftsScreenState = ShiftsScreenState(listOf(testShift), listOf())
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(screenState.shifts) { shift ->
            ShiftsItem(shift, screenState.contextMenu)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShiftsScreen() {
    ShiftsScreenUI()
}