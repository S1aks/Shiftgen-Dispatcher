package com.s1aks.shiftgen_dispatcher.ui.screens.content.structure

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.ui.elements.DoneIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.AppBarState

@Composable
fun StructureScreen(
    navController: NavHostController,
    onComposing: (AppBarState) -> Unit,
    viewModel: StructureViewModel
) {
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = "Редактировать структуру",
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = false) { navController.popBackStack() }
                }
            )
        )
    }
}