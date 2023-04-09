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
import com.s1aks.shiftgen_dispatcher.ui.screens.auth.login.LoginScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.auth.register.RegisterScreen
import com.s1aks.shiftgen_dispatcher.ui.screens.content.MainScreen
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
                    NavHost(navController = navController, startDestination = "login") {
                        composable("main") { MainScreen() }
                        composable("login") { LoginScreen(navController) }
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
        MainScreen()
    }
}