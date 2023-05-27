package com.s1aks.shiftgen_dispatcher.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.s1aks.shiftgen_dispatcher.ui.screens.auth.login.LoginScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.auth.register.RegisterScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.AppBarState
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.direction_add.DirectionAddScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.directions.DirectionsScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.shift_add.ShiftAddScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts.ShiftsScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.worker_add.WorkerAddScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.workers.WorkersScreen
import org.koin.androidx.compose.koinViewModel

enum class NavRoutes {
    StartRoute,
    MainRoute
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
    object Shifts : Screen("shifts")
    object ShiftAdd : Screen("shift_add")
    object Workers : Screen("workers")
    object WorkerAdd : Screen("worker_add")
    object Directions : Screen("directions")
    object DirectionAdd : Screen("direction_add")
}

fun NavController.clearAndNavigate(route: String) {
    backQueue.clear()
    navigate(route)
}

fun NavGraphBuilder.startGraph(navController: NavHostController) {
    composable(Screen.Login.route) {
        LoginScreen(navController = navController, viewModel = koinViewModel())
    }
    composable(Screen.Register.route) {
        RegisterScreen(navController = navController, viewModel = koinViewModel())
    }
    composable(Screen.Main.route) {
        MainScreen(navController = navController)
    }
}

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    drawerEnable: (Boolean) -> Unit,
    onComposing: (AppBarState) -> Unit
) {
    composable(Screen.Shifts.route) {
        drawerEnable(true)
        ShiftsScreen(
            navController = navController,
            onComposing = onComposing,
            viewModel = koinViewModel()
        )
    }
    composable(Screen.ShiftAdd.route) {
        drawerEnable(false)
        ShiftAddScreen(
            navController = navController,
            onComposing = onComposing,
            viewModel = koinViewModel()
        )
    }
    composable(Screen.Workers.route) {
        drawerEnable(true)
        WorkersScreen(
            navController = navController,
            onComposing = onComposing,
            viewModel = koinViewModel()
        )
    }
    composable(Screen.WorkerAdd.route) {
        drawerEnable(false)
        WorkerAddScreen(
            navController = navController,
            onComposing = onComposing,
            viewModel = koinViewModel()
        )
    }
    composable(Screen.Directions.route) {
        drawerEnable(true)
        DirectionsScreen(
            navController = navController,
            onComposing = onComposing,
            viewModel = koinViewModel()
        )
    }
    composable(Screen.DirectionAdd.route) {
        drawerEnable(false)
        DirectionAddScreen(
            navController = navController,
            onComposing = onComposing,
            viewModel = koinViewModel()
        )
    }
}
