package com.s1aks.shiftgen_dispatcher.ui.screens.content.worker_add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.ui.screens.content.AppBarState

@Composable
fun WorkerAddScreen(
    navController: NavHostController,
    onComposing: (AppBarState) -> Unit,
    viewModel: WorkerAddViewModel
) {
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = "Добавить рабочего",
                actions = {}
            )
        )
    }
}