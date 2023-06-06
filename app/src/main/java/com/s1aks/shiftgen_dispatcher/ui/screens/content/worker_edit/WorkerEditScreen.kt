package com.s1aks.shiftgen_dispatcher.ui.screens.content.worker_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.ui.elements.DoneIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState

@Composable
fun WorkerEditScreen(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: WorkerEditViewModel,
    id: Int
) {
    LaunchedEffect(Unit) {
        onComposing(
            MainScreenState(
                title = "Добавить рабочего",
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = false) { navController.popBackStack() }
                }
            )
        )
    }
}