package com.s1aks.shiftgen_dispatcher.ui.screens.content.workers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.elements.AddIconButton
import com.s1aks.shiftgen_dispatcher.ui.elements.ContextMenuItem
import com.s1aks.shiftgen_dispatcher.ui.elements.LoadingIndicator
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState
import com.s1aks.shiftgen_dispatcher.utils.onSuccess

@Composable
fun WorkersScreen(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: WorkersViewModel
) {
    LaunchedEffect(Unit) {
        onComposing(
            MainScreenState(
                title = "Рабочие",
                drawerEnabled = true,
                actions = {
                    AddIconButton {
                        viewModel.clearStates()
                        navController.navigate(Screen.WorkerEdit("0").route)
                    }
                }
            )
        )
        viewModel.getData()
    }
    val screenState: WorkersScreenState by remember {
        mutableStateOf(WorkersScreenState(
            workers = listOf(),
            contextMenu = listOf(
                ContextMenuItem("Редактировать") { id ->
                    viewModel.clearStates()
                    navController.navigate(Screen.WorkerEdit(id.toString()).route)
                },
                ContextMenuItem("Удалить") { id ->
                    viewModel.deleteData(id)
                }
            )
        ))
    }
    var loadingState by rememberSaveable { mutableStateOf(false) }
    val responseState by viewModel.workersState.collectAsState()
    responseState.onSuccess(LocalContext.current, { loadingState = it }) {
        screenState.workers = (responseState as ResponseState.Success).item
    }
    if (loadingState) {
        LoadingIndicator()
    } else {
        WorkersScreenUI(screenState)
    }
}

data class WorkersScreenState(
    var workers: List<Worker>,
    val contextMenu: List<ContextMenuItem>
)

@Composable
fun WorkersScreenUI(
    screenState: WorkersScreenState = WorkersScreenState(listOf(testWorker), listOf())
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(screenState.workers) { worker ->
            WorkersItem(worker, screenState.contextMenu)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWorkersScreen() {
    WorkersScreenUI()
}