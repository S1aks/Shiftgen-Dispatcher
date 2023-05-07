package com.s1aks.shiftgen_dispatcher.ui.screens.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Shift
import com.s1aks.shiftgen_dispatcher.ui.NAV_DIRECTIONS
import com.s1aks.shiftgen_dispatcher.ui.NAV_LOGIN
import com.s1aks.shiftgen_dispatcher.ui.NAV_SHIFTS
import com.s1aks.shiftgen_dispatcher.ui.NAV_WORKERS
import com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts.ShiftsScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts.ShiftsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: ShiftsViewModel
) {
    MainScreenUI(
        viewModel.shiftsState,
        onExit = {
            navController.backQueue.clear()
            navController.navigate(NAV_LOGIN)
        }
    )
}


@Composable
fun DrawerContent(navController: NavController, onExit: () -> Unit) {
    Text(
        modifier = Modifier
            .padding(5.dp)
            .clickable(enabled = false) {
                navController.navigate(NAV_SHIFTS)
            },
        text = "Смены"
    )
    Divider()
    Text(
        modifier = Modifier
            .padding(5.dp)
            .clickable(enabled = true) {
                navController.navigate(NAV_WORKERS)
            },
        text = "Рабочие"
    )
    Divider()
    Text(
        modifier = Modifier
            .padding(5.dp)
            .clickable(enabled = true) {
                navController.navigate(NAV_DIRECTIONS)
            },
        text = "Направления"
    )
    Divider()
    Text(
        modifier = Modifier
            .clickable { onExit() },
        text = "Выход"
    )
}

@Composable
fun MainScreenUI(
    shiftsStateFlow: StateFlow<ResponseState<List<Shift>>>,
    onExit: () -> Unit
) {
    val mainNavController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { DrawerContent(mainNavController, { onExit() }) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
        ) {
            NavHost(
                navController = mainNavController,
                startDestination = NAV_SHIFTS
            ) {
                composable(NAV_SHIFTS) {
                    ShiftsScreen(
                        navController = mainNavController,
//                        viewModel = koinViewModel<ShiftsViewModel>()
                    )
                }
                composable(NAV_WORKERS) {
                    ShiftsScreen(
                        navController = mainNavController,
//                        viewModel = koinViewModel<ShiftsViewModel>()
                    )
                }
                composable(NAV_DIRECTIONS) {
                    ShiftsScreen(
                        navController = mainNavController,
//                        viewModel = koinViewModel<ShiftsViewModel>()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreenUI(MutableStateFlow(ResponseState.Idle)) {}
}