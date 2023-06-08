package com.s1aks.shiftgen_dispatcher.ui.screens.content.direction_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun DirectionAccessItem(
    directionName: String = "Direction",
    checked: Boolean = false,
    onChange: (Boolean) -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = directionName)
            Spacer(modifier = Modifier.weight(1.0f))
            Switch(checked = checked, onCheckedChange = onChange)
        }
        Divider(color = MaterialTheme.colors.primaryVariant, thickness = 1.dp)
    }
}