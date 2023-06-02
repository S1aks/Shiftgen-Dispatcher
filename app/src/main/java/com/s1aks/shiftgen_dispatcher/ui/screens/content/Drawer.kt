package com.s1aks.shiftgen_dispatcher.ui.screens.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Route
import androidx.compose.runtime.Composable
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.s1aks.shiftgen_dispatcher.R
import com.s1aks.shiftgen_dispatcher.ui.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerHeader() {
    Text(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        text = stringResource(id = R.string.app_name),
        color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
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
    val icon: ImageVector?,
    val route: String,
    val isDivider: Boolean = false,
) {
    object Divider : MainNavItem("", null, "", true)
    object Shifts : MainNavItem("Смены", Icons.Default.ListAlt, Screen.Shifts.route)
    object Workers : MainNavItem("Рабочие", Icons.Default.Groups, Screen.Workers.route)
    object Directions : MainNavItem("Направления", Icons.Default.Route, Screen.Directions.route)
    object Structure : MainNavItem("Структура", Icons.Default.Domain, Screen.Structure.route)
    object Exit : MainNavItem("Выход", Icons.Default.Logout, Screen.Login.route)
}

@Composable
fun DrawerItem(
    item: MainNavItem,
    selected: Boolean,
    onItemClick: () -> Unit
) {
    if (item.isDivider) {
        Divider(
            thickness = 20.dp,
            color = Color.Transparent
        )
    } else {
        Button(
            modifier = Modifier
                .height(50.dp)
                .padding(horizontal = 10.dp, vertical = 2.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = MaterialTheme.colors.onSurface,
            ),
            border = BorderStroke(
                1.dp,
                if (selected) MaterialTheme.colors.onSurface else MaterialTheme.colors.surface
            ),
            enabled = selected,
            onClick = { onItemClick() })
        {
            Icon(
                modifier = Modifier
                    .scale(1.2f)
                    .padding(horizontal = 10.dp)
                    .offset(y = 0.dp),
                imageVector = item.icon
                    ?: throw RuntimeException("Ошибка иконки бокового меню"),
                contentDescription = item.title
            )
            Text(
                text = item.title, fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun DrawerContent(
    drawerItems: List<MainNavItem> = listOf(
        MainNavItem.Shifts,
        MainNavItem.Workers,
        MainNavItem.Directions,
        MainNavItem.Divider,
        MainNavItem.Structure,
        MainNavItem.Divider,
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
            if (item !is MainNavItem.Divider) {
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
