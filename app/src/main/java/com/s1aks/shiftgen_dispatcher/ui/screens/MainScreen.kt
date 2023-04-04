package com.s1aks.shiftgen_dispatcher.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen() {
    Scaffold() {
        Text(modifier = Modifier.padding(it), text = "Like")
    }
}