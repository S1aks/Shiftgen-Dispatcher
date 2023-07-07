package com.s1aks.shiftgen_dispatcher.ui.screens.content.shift_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun BottomSheetContent() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            Text(
                text = "Список временных блоков",
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp),
            )
            TextButton(
                onClick = {}
            ) {
                Text(
                    text = "Добавить",
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        Divider(
            modifier = Modifier.padding(5.dp),
        )
    }
}
