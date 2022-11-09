package com.youme.naya.schedule.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.youme.naya.database.entity.Schedule
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.Typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScheduleItem(
    modifier: Modifier = Modifier,
    schedule: Schedule,
    selectedDate: String,
    onDetailSchedule: () -> Unit,
    viewModel: ScheduleMainViewModel = hiltViewModel(),
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp),
        elevation = 3.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = onDetailSchedule
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = "${selectedDate}",
                    style = Typography.h6.copy(color= PrimaryDark)
                )
                Text(
                    text = "${schedule.title}, ${schedule.description}",
                    style = Typography.h6.copy(color= PrimaryDark)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Checkbox(
                    checked = schedule.isDone,
                    onCheckedChange = { isChecked ->
                        viewModel.onDoneChange(schedule, isChecked)
                    }
                )
            }
        }
    }
}