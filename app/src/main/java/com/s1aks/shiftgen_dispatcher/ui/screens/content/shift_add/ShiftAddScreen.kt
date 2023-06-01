package com.s1aks.shiftgen_dispatcher.ui.screens.content.shift_add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.ui.elements.DoneIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState

@Composable
fun ShiftAddScreen(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: ShiftAddViewModel
) {
    LaunchedEffect(Unit) {
        onComposing(
            MainScreenState(
                title = "Добавить смену",
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = false) { navController.popBackStack() }
                }
            )
        )
    }
}