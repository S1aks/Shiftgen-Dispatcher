package com.s1aks.shiftgen_dispatcher.ui.elements

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun AddIconButton(
    onClick: () -> Unit = {}
) {
    IconButton(onClick = { onClick() }) {
        Icon(imageVector = Icons.Default.Add, "Добавить")
    }
}

@Preview(showBackground = true)
@Composable
fun OkIconButton(
    onClick: () -> Unit = {}
) {
    IconButton(onClick = { onClick() }) {
        Icon(imageVector = Icons.Default.Done, "Подтвердить")
    }
}