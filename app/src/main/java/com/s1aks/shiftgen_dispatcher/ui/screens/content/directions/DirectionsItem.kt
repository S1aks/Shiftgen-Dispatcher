package com.s1aks.shiftgen_dispatcher.ui.screens.content.directions

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.ui.elements.ContextMenuItem

internal val testDirection = Direction(
    1,
    "Ростов. Пригородный автовокзал."
)

@Preview(showBackground = true)
@Composable
fun DirectionsItem(
    direction: Direction = testDirection,
    contextMenu: List<ContextMenuItem> = listOf()
) {
    var isContextMenuVisible by rememberSaveable { mutableStateOf(false) }
    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { itemHeight = it.height.dp }
            .pointerInput(true) {
                detectTapGestures(onLongPress = {
                    isContextMenuVisible = true
                    pressOffset = DpOffset(it.x.dp, it.y.dp)
                })
            }
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = direction.name
        )
        Divider(color = MaterialTheme.colors.primaryVariant, thickness = 1.dp)
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            contextMenu.forEach { item ->
                DropdownMenuItem(onClick = {
                    item.action(direction.id)
                    isContextMenuVisible = false
                }) {
                    Text(text = item.label)
                }
            }
        }

    }
}
