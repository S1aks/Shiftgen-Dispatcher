package com.s1aks.shiftgen_dispatcher.ui.screens.content.workers

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.elements.AddIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.AppBarState

@Composable
fun WorkersScreen(
    navController: NavHostController,
    onComposing: (AppBarState) -> Unit,
    viewModel: WorkersViewModel
) {
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = "Рабочие",
                drawerEnabled = true,
                actions = {
                    AddIconButton { navController.navigate(Screen.WorkerAdd.route) }
                }
            )
        )
    }
    Text(text = "Workers")
}
