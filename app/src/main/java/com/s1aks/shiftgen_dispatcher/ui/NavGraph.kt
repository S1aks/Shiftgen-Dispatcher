package com.s1aks.shiftgen_dispatcher.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.s1aks.shiftgen_dispatcher.ui.screens.auth.login.LoginScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.auth.register.RegisterScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreenState
import com.s1aks.shiftgen_dispatcher.ui.screens.content.direction_edit.DirectionEditScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.directions.DirectionsScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.shift_edit.ShiftEditScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts.ShiftsScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.structure.StructureScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.worker_edit.WorkerEditScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.workers.WorkersScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

enum class NavRoutes {
    StartRoute,
    MainRoute
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
    object Structure : Screen("structure")
    object Directions : Screen("directions")
    object DirectionEdit : Screen("direction_edit/{id}")
    object Shifts : Screen("shifts")
    object ShiftEdit : Screen("shift_edit/{id}")
    object Workers : Screen("workers")
    object WorkerEdit : Screen("worker_edit/{id}")
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
        MainScreen(navController = navController, localSecureStore = koinInject())
    }
}

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit
) {
    composable(Screen.Structure.route) {
        StructureScreen(
            navController = navController,
            onComposing = onComposing,
            viewModel = koinViewModel()
        )
    }
    composable(Screen.Directions.route) {
        DirectionsScreen(
            navController = navController,
            onComposing = onComposing,
            viewModel = koinViewModel()
        )
    }
    composable(Screen.DirectionEdit.route) { backStackEntry ->
        DirectionEditScreen(
            navController = navController,
            onComposing = onComposing,
            viewModel = koinViewModel(),
            backStackEntry.arguments?.getInt("id") ?: -1
        )
    }
    composable(Screen.Shifts.route) {
        ShiftsScreen(
            navController = navController,
            onComposing = onComposing,
            viewModel = koinViewModel()
        )
    }
    composable(Screen.ShiftEdit.route) {
        ShiftEditScreen(
            navController = navController,
            onComposing = onComposing,
            viewModel = koinViewModel()
        )
    }
    composable(Screen.Workers.route) {
        WorkersScreen(
            navController = navController,
            onComposing = onComposing,
            viewModel = koinViewModel()
        )
    }
    composable(Screen.WorkerEdit.route) {
        WorkerEditScreen(
            navController = navController,
            onComposing = onComposing,
            viewModel = koinViewModel()
        )
    }
}
