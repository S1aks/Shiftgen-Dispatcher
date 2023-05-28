package com.s1aks.shiftgen_dispatcher.ui.screens.content.shift_add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.ui.elements.DoneIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.AppBarState

@Composable
fun ShiftAddScreen(
    navController: NavHostController,
    onComposing: (AppBarState) -> Unit,
    viewModel: ShiftAddViewModel
) {
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = "Добавить смену",
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = false) { navController.popBackStack() }
                }
            )
        )
    }
}