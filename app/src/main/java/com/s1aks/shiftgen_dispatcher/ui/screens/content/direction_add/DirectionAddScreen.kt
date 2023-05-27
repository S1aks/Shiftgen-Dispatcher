package com.s1aks.shiftgen_dispatcher.ui.screens.content.direction_add

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.ui.Screen
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
                actions = {}
            )
        )
    }
}