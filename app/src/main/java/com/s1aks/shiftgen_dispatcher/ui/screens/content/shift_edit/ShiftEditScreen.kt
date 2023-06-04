package com.s1aks.shiftgen_dispatcher.ui.screens.content.shift_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.ui.elements.DoneIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState

@Composable
fun ShiftEditScreen(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: ShiftEditViewModel
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