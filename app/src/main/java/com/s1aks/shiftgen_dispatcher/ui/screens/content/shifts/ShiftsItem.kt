package com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts

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
import androidx.constraintlayout.compose.ConstraintLayout
import com.s1aks.shiftgen_dispatcher.domain.models.ShiftModel

private val testShift = ShiftModel(
    "25.05.2023",
    "20:00",
    "Рейс 512",
    "Москва - Ростов",
    "Михайлов Н.А.",
    "14:25"
)

@Preview(showBackground = true)
@Composable
fun ShiftsItem(
    shift: ShiftModel = testShift   // todo REMOVE test
) {
    Column {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
        ) {
            val (date, time, shift_name, worker, work_time) = createRefs()
            val startGuideline = createGuidelineFromStart(0.25f)
            val endGuideline = createGuidelineFromStart(0.85f)
            Text(text = shift.start_date,
                modifier = Modifier
                    .constrainAs(date) {
                        start.linkTo(parent.start)
                        end.linkTo(startGuideline)
                        top.linkTo(parent.top)
                        bottom.linkTo(time.top)
                    }
            )
            Text(text = shift.start_time,
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
            Text(text = shift.work_time,
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
    }
}
