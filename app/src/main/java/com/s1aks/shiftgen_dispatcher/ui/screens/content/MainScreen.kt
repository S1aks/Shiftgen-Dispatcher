package com.s1aks.shiftgen_dispatcher.ui.screens.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Route
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.ui.NavRoutes
import com.s1aks.shiftgen_dispatcher.ui.Screen
import com.s1aks.shiftgen_dispatcher.ui.clearAndNavigate
import com.s1aks.shiftgen_dispatcher.ui.mainGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

@Composable
fun MainScreen(
    navController: NavController
) {
    val localSecureStore: LocalSecureStore by inject(LocalSecureStore::class.java)
    MainScreenUI(
        onLogout = {
            localSecureStore.clear()
            navController.clearAndNavigate(Screen.Login.route)
        }
    )
}

@Composable
fun DrawerHeader() {
    Text(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        text = stringResource(id = R.string.app_name),
        color = colors.primaryVariant,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
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
                .scale(1.0f),
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
    Button(
        colors = buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = colors.primary,
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, if (selected) colors.primary else colors.secondary),
        modifier = Modifier
            .height(50.dp)
            .padding(horizontal = 10.dp, vertical = 2.dp)
            .fillMaxWidth(),
        enabled = selected,
        onClick = { onItemClick() })
    {
        Icon(
            modifier = Modifier
                .scale(1.2f)
                .padding(horizontal = 10.dp)
                .offset(y = 0.dp),
            imageVector = item.icon,
            contentDescription = item.title
        )
        Text(
            text = item.title, fontSize = 18.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth(),
        )
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

data class AppBarState(
    val title: String = "",
    val drawerEnabled: Boolean = true,
    val actions: (@Composable RowScope.() -> Unit)? = null
)

@Composable
fun MainScreenUI(
    onLogout: () -> Unit = {}
) {
    val mainNavController = rememberNavController()
    var appBarState by remember { mutableStateOf(AppBarState()) }
    val scaffoldState =
        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                title = appBarState.title,
                navigationIconVisible = appBarState.drawerEnabled,
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                },
                actions = { appBarState.actions?.invoke(this) }
            )
        },
        drawerContent = {
            if (appBarState.drawerEnabled) {
                DrawerContent(
                    navController = mainNavController,
                    scope = scope,
                    drawerState = scaffoldState.drawerState,
                    onLogout = onLogout
                )
            }
        },
        drawerGesturesEnabled = appBarState.drawerEnabled
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
                mainGraph(mainNavController) { appBarState = it }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreenUI()
}