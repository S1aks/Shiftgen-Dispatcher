package com.s1aks.shiftgen_dispatcher.ui.screens.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.api.KtorClient
import com.s1aks.shiftgen_dispatcher.ui.NavRoutes
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.clearAndNavigate
import com.s1aks.shiftgen_dispatcher.ui.mainGraph
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavController,
    localSecureStore: LocalSecureStore,
    client: KtorClient
) {
    MainScreenUI(
        userName = localSecureStore.login.toString(),
        onLogout = {
            localSecureStore.clear()
            client.clearTokens()
            navController.clearAndNavigate(Screen.Login.route)
        }
    )
}

data class MainScreenState(
    val title: @Composable () -> Unit = { Text("") },
    val drawerEnabled: Boolean = true,
    val actions: (@Composable RowScope.() -> Unit)? = null
)

@Composable
fun MainScreenUI(
    userName: String = "",
    onLogout: () -> Unit = {}
) {
    val mainNavController = rememberNavController()
    var screenState by remember { mutableStateOf(MainScreenState()) }
    val scaffoldState =
        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                title = screenState.title,
                navigationIconVisible = screenState.drawerEnabled,
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            ) { screenState.actions?.invoke(this) }
        },
        drawerContent = {
            if (screenState.drawerEnabled) {
                DrawerContent(
                    navController = mainNavController,
                    scope = scope,
                    drawerState = scaffoldState.drawerState,
                    userName = userName,
                    onLogout = onLogout
                )
            }
        },
        drawerGesturesEnabled = screenState.drawerEnabled
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NavHost(
                navController = mainNavController,
                startDestination = Screen.Shifts.route,
                route = NavRoutes.MainRoute.name
            ) {
                mainGraph(mainNavController) { screenState = it }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreenUI()
}