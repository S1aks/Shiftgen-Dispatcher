package com.s1aks.shiftgen_dispatcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.s1aks.shiftgen_dispatcher.ui.screens.LoadingScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.LoginScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.MainScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.RegisterScreen
import com.s1aks.shiftgen_dispatcher.ui.theme.ShiftgenDispatcherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShiftgenDispatcherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") { MainScreen() }
                        composable("login") { LoginScreen() }
                        composable("register") { RegisterScreen() }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShiftgenDispatcherTheme {
        LoadingScreen()
    }
}