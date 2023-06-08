package com.s1aks.shiftgen_dispatcher.ui.screens.content.directions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.elements.AddIconButton
import com.s1aks.shiftgen_dispatcher.ui.elements.LoadingIndicator
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
                    AddIconButton { navController.navigate(Screen.DirectionEdit("0").route) }
                }
            )
        )
        viewModel.getData()
    }
    val screenState: DirectionsScreenState by remember {
        mutableStateOf(DirectionsScreenState(
            directions = listOf(),
            contextMenu = listOf(
                ContextMenuItem("Редактировать") { id ->
                    navController.navigate(Screen.DirectionEdit(id.toString()).route)
                },
                ContextMenuItem("Удалить") { id ->
                    viewModel.deleteData(id)
                }
            )
        ))
    }
    var loadingState by rememberSaveable { mutableStateOf(false) }
    val responseState by viewModel.directionsState.collectAsState()
    responseState.onSuccess(LocalContext.current, { loadingState = it }) {
        screenState.directions = (responseState as ResponseState.Success).item
    }
    if (loadingState) {
        LoadingIndicator()
    } else {
        DirectionsScreenUI(screenState)
    }
}

data class DirectionsScreenState(
    var directions: List<Direction>,
    val contextMenu: List<ContextMenuItem>
)

data class ContextMenuItem(
    val label: String,
    val action: (id: Int) -> Unit
)

@Composable
fun DirectionsScreenUI(
    screenState: DirectionsScreenState = DirectionsScreenState(listOf(testDirection), listOf())
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(screenState.directions) { direction ->
            DirectionItem(direction, screenState.contextMenu)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDirectionsScreen() {
    DirectionsScreenUI()
}