package com.s1aks.shiftgen_dispatcher.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.s1aks.shiftgen_dispatcher.ui.NavRoutes
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.startGraph
import com.s1aks.shiftgen_dispatcher.ui.theme.ShiftgenDispatcherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        org.apache.log4j.BasicConfigurator.configure()  // Logger HTTP API requests
        setContent {
            ShiftgenDispatcherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Login.route,
                        route = NavRoutes.StartRoute.name
                    ) {
                        startGraph(navController)
                    }
                }
            }
        }
    }
}
