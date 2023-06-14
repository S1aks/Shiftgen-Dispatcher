package com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts

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
import androidx.constraintlayout.compose.ConstraintLayout
import com.s1aks.shiftgen_dispatcher.domain.models.ShiftItemModel
import com.s1aks.shiftgen_dispatcher.ui.elements.ContextMenuItem

private val testShift = ShiftItemModel(
    0,
    "Москва - Ростов",
    "Михайлов Н.А.",
    "Москва",
    "",
    "",
    "20"
)

@Preview(showBackground = true)
@Composable
fun ShiftsItem(
    shift: ShiftItemModel = testShift,   // todo REMOVE test
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
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
        ) {
            val (date, time, shift_name, worker, work_time) = createRefs()
            val startGuideline = createGuidelineFromStart(0.25f)
            val endGuideline = createGuidelineFromStart(0.85f)
            Text(text = shift.startDay,
                modifier = Modifier
                    .constrainAs(date) {
                        start.linkTo(parent.start)
                        end.linkTo(startGuideline)
                        top.linkTo(parent.top)
                        bottom.linkTo(time.top)
                    }
            )
            Text(text = shift.startTime,
                modifier = Modifier
                    .constrainAs(time) {
                        start.linkTo(parent.start)
                        end.linkTo(startGuideline)
                        top.linkTo(date.bottom)
                        bottom.linkTo(parent.bottom)
                    }
            )
            Text(text = "${shift.name} ${shift.direction}",
                modifier = Modifier
                    .constrainAs(shift_name) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(parent.top)
                        bottom.linkTo(worker.top)
                    }
            )
            Text(text = shift.worker,
                modifier = Modifier
                    .constrainAs(worker) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(shift_name.bottom)
                        bottom.linkTo(parent.bottom)
                    }
            )
            Text(text = shift.workTime,
                modifier = Modifier
                    .constrainAs(work_time) {
                        start.linkTo(endGuideline)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
        Divider(
            color = MaterialTheme.colors.primaryVariant,
            thickness = 1.dp
        )
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            contextMenu.forEach { item ->
                DropdownMenuItem(onClick = {
                    item.action(shift.id)
                    isContextMenuVisible = false
                }) {
                    Text(text = item.label)
                }
            }
        }
    }
}
