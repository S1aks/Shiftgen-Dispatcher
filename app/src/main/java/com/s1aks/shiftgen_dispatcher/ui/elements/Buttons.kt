package com.s1aks.shiftgen_dispatcher.ui.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun AddIconButton(
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = Modifier.border(
            width = 1.dp,
            color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
            shape = CircleShape
        ),
        onClick = { onClick() }
    ) {
        Icon(imageVector = Icons.Default.Add, "Добавить")
    }
}

@Preview(showBackground = true)
@Composable
fun DoneIconButton(
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = Modifier.border(
            width = 1.dp,
            color = if (enabled)
                LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
            else
                colors.error,
            shape = CircleShape
        ),
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Icon(imageVector = Icons.Default.Done, "Подтвердить")
    }
}

@Preview(showBackground = true)
@Composable
fun PrevIconButton(
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    IconButton(
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIos,
            contentDescription = "Подтвердить",
            tint = if (enabled) Color.Green else Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NextIconButton(
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    IconButton(
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "Подтвердить",
            tint = if (enabled) Color.Green else Color.Gray
        )
    }
}