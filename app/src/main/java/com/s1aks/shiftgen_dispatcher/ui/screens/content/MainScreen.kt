package com.s1aks.shiftgen_dispatcher.ui.screens.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.s1aks.shiftgen_dispatcher.R
import com.s1aks.shiftgen_dispatcher.ui.NavRoutes
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.clearAndNavigate
import com.s1aks.shiftgen_dispatcher.ui.mainGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavController
) {
    MainScreenUI(
        onLogout = {
            navController.clearAndNavigate(Screen.Login.route)
        }
    )
}

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .size(100.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .matchParentSize(),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "",
        )
        Image(
            modifier = Modifier
                .scale(1.4f),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "",
        )
    }
}

sealed class MainNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    companion object {
        @JvmStatic
        fun values() =
            MainNavItem::class.sealedSubclasses.map { it.objectInstance as MainNavItem }
    }

    object Shifts : MainNavItem("Смены", Icons.Default.ListAlt, Screen.Shifts.route)
    object Workers : MainNavItem("Рабочие", Icons.Default.Groups, Screen.Workers.route)
    object Directions : MainNavItem("Направления", Icons.Default.Route, Screen.Directions.route)
    object Exit : MainNavItem("Выход", Icons.Default.Logout, Screen.Login.route)
}

@Composable
fun DrawerItem(
    item: MainNavItem,
    selected: Boolean,
    onItemClick: () -> Unit
) {
    TextButton(
        modifier = Modifier.height(50.dp),
        enabled = selected,
        onClick = { onItemClick() })
    {
        Icon(
            modifier = Modifier
                .scale(1.6f)
                .padding(horizontal = 16.dp)
                .offset(y = 2.dp),
            imageVector = item.icon,
            contentDescription = item.title
        )
        Text(text = item.title, fontSize = 28.sp)
    }
}

@Composable
fun DrawerContent(
    drawerItems: List<MainNavItem> = listOf(
        MainNavItem.Shifts,
        MainNavItem.Workers,
        MainNavItem.Directions,
        MainNavItem.Exit
    ),
    navController: NavController? = null,
    scope: CoroutineScope? = null,
    drawerState: DrawerState? = null,
    onLogout: () -> Unit = {}
) {
    val navBackStackEntry = navController?.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.value?.destination?.route
    val itemClick: (MainNavItem) -> Unit = { item ->
        if (item.route == Screen.Login.route) {
            onLogout()
        } else {
            navController?.navigate(item.route) {
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        }
        scope?.launch {
            drawerState?.close()
        }
    }
    Column {
        DrawerHeader()
        LazyColumn {
            items(drawerItems) { item ->
                DrawerItem(
                    item = item,
                    selected = currentRoute != item.route,
                    onItemClick = { itemClick(item) }
                )
            }
        }
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = "S1aks @ 2023",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDrawerContent() {
    DrawerContent()
}

@Composable
fun MainScreenUI(
    onLogout: () -> Unit = {}
) {
    val mainNavController = rememberNavController()
    val scaffoldState =
        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            )
        },
        drawerContent = {
            DrawerContent(
                navController = mainNavController,
                scope = scope,
                drawerState = scaffoldState.drawerState,
                onLogout = onLogout
            )
        }
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
                startDestination = Screen.Shifts.route,
                route = NavRoutes.MainRoute.name
            ) {
                mainGraph(mainNavController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreenUI()
}