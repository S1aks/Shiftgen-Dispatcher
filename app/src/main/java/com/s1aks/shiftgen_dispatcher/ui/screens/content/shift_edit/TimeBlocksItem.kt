package com.s1aks.shiftgen_dispatcher.ui.screens.content.shift_edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.s1aks.shiftgen_dispatcher.data.entities.Action
import com.s1aks.shiftgen_dispatcher.data.entities.TimeBlock
import com.s1aks.shiftgen_dispatcher.utils.toTimeString

internal val testTimeBlock = TimeBlock(
    5,
    "Москва туда",
    89500000L,
    Action.WORK
)

@Preview(showBackground = true)
@Composable
fun TimeBlocksItem(timeBlock: TimeBlock = testTimeBlock) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${timeBlock.name} (${timeBlock.duration.toTimeString()})")
            Spacer(modifier = Modifier.weight(1.0f))
            Icon(
                Icons.Filled.ArrowUpward, "Переместить вверх",
                Modifier
                    .clickable {

                    }
                    .height(36.dp)
                    .width(36.dp),
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                Icons.Filled.ArrowDownward, "Переместить вниз",
                Modifier
                    .clickable {

                    }
                    .height(36.dp)
                    .width(36.dp),
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                Icons.Filled.Delete, "Удалить",
                Modifier
                    .clickable {

                    }
                    .height(36.dp)
                    .width(36.dp),
                tint = Color.Red
            )
        }
        Divider(
            color = MaterialTheme.colors.primaryVariant,
            thickness = 1.dp
        )
    }
}

