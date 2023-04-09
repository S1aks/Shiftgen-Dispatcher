package com.s1aks.shiftgen_dispatcher.ui.screens.content

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.s1aks.shiftgen_dispatcher.ui.screens.LoadingScreen

@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Text(modifier = Modifier.padding(5.dp), text = "Смены")
            Divider()
            Text(modifier = Modifier.padding(5.dp), text = "Рабочие")
            Divider()
            Text(modifier = Modifier.padding(5.dp), text = "Направления")
        }
    ) {
        Text(modifier = Modifier.padding(it), text = "Like")
        LoadingScreen()
    }
}
