package com.s1aks.shiftgen_dispatcher.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.s1aks.shiftgen_dispatcher.ui.screens.auth.login.LoginScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.auth.register.RegisterScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.directions.DirectionsScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts.ShiftsScreen
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
    object Workers : Screen("workers")
    object Directions : Screen("directions")
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

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    composable(Screen.Shifts.route) {
        ShiftsScreen(navController = navController, viewModel = koinViewModel())
    }
    composable(Screen.Workers.route) {
        WorkersScreen(navController = navController, viewModel = koinViewModel())
    }
    composable(Screen.Directions.route) {
        DirectionsScreen(navController = navController, viewModel = koinViewModel())
    }
}