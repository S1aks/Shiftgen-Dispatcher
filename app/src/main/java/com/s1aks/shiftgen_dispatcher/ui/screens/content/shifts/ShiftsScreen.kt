package com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun ShiftsScreen(navController: NavHostController, viewModel: ShiftsViewModel) {
    Text(text = "Shifts")
}
