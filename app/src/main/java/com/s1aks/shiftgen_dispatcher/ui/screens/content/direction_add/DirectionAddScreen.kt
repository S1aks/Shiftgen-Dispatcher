package com.s1aks.shiftgen_dispatcher.ui.screens.content.direction_add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.ui.elements.DoneIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.AppBarState

@Composable
fun DirectionAddScreen(
    navController: NavHostController,
    onComposing: (AppBarState) -> Unit,
    viewModel: DirectionAddViewModel
) {
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = "Добавить направление",
                actions = {
                    DoneIconButton(enabled = false) { navController.popBackStack() }
                }
            )
        )
    }
}