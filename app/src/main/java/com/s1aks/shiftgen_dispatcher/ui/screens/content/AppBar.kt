package com.s1aks.shiftgen_dispatcher.ui.screens.content

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AppBar(
    title: @Composable () -> Unit = {},
    navigationIconVisible: Boolean = true,
    onNavigationIconClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { title.invoke() },
        navigationIcon = {
            if (navigationIconVisible) {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Боковое меню"
                    )
                }
            }
        },
        actions = actions
    )
}

@Preview
@Composable
fun PreviewAppBar() {
    AppBar()
}