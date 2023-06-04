package com.s1aks.shiftgen_dispatcher.ui.screens.content.directions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.s1aks.shiftgen_dispatcher.data.entities.Direction

private val testDirection = Direction(
    1,
    "Ростов. Пригородный автовокзал."
)

@Preview(showBackground = true)
@Composable
fun DirectionItem(
    direction: Direction = testDirection
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = direction.name
        )
        Divider(color = MaterialTheme.colors.primaryVariant, thickness = 1.dp)
    }
}
