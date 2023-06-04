package com.s1aks.shiftgen_dispatcher.ui.screens.content.workers

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.elements.AddIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState

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
                    AddIconButton { navController.navigate(Screen.WorkerEdit.route) }
                }
            )
        )
    }
    Text(text = "Workers")
}
