package com.s1aks.shiftgen_dispatcher.ui.screens.content.directions

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.AppBarState

@Composable
fun DirectionsScreen(
    navController: NavHostController,
    onComposing: (AppBarState) -> Unit,
    viewModel: DirectionsViewModel
) {
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = "Направления",
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.DirectionAdd.route) }) {
                        Icon(imageVector = Icons.Default.Add, "")
                    }
                }
            )
        )
    }
    Text(text = "Directions")
}
