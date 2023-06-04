package com.s1aks.shiftgen_dispatcher.ui.screens.content.directions

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.elements.AddIconButton
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState
import com.s1aks.shiftgen_dispatcher.utils.onSuccess

@Composable
fun DirectionsScreen(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: DirectionsViewModel
) {
    LaunchedEffect(Unit) {
        onComposing(
            MainScreenState(
                title = "Направления",
                drawerEnabled = true,
                actions = {
                    AddIconButton { navController.navigate(Screen.DirectionEdit.route) }
                }
            )
        )
    }
    val screenState: DirectionsScreenState by remember {
        mutableStateOf(DirectionsScreenState(listOf()))
    }
    var loadingState by rememberSaveable { mutableStateOf(false) }
    val responseState by viewModel.directionsState.collectAsState()
    responseState.onSuccess(LocalContext.current, { loadingState = it }) {
        screenState.directions = (responseState as ResponseState.Success).item
    }
    if (loadingState) {
        CircularProgressIndicator()
    } else {
        DirectionsScreenUI(screenState)
    }
    if (loadingState) {
        CircularProgressIndicator()
    } else {
        DirectionsScreenUI(screenState)
    }
}

data class DirectionsScreenState(
    var directions: List<Direction>
)

@Composable
fun DirectionsScreenUI(
    screenState: DirectionsScreenState
) {
    LazyColumn {
        items(screenState.directions) { direction ->
            DirectionItem(direction)
        }
    }
}