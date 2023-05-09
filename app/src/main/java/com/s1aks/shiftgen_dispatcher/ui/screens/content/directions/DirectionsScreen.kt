package com.s1aks.shiftgen_dispatcher.ui.screens.content.directions

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun DirectionsScreen(navController: NavHostController, viewModel: DirectionsViewModel) {
    Text(text = "Directions")
}
