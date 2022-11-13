package com.youme.naya.schedule.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.youme.naya.database.entity.Schedule
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScheduleItem(
    modifier: Modifier = Modifier,
    schedule: Schedule,
    viewModel: ScheduleMainViewModel = hiltViewModel(),
    navController: NavController
) {
    Row(
        modifier = Modifier.height(80.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Checkbox(
            modifier = Modifier.scale(0.9F).fillMaxWidth(0.12f),
            checked = schedule.isDone,
            onCheckedChange = { isChecked ->
                schedule.scheduleId?.let { viewModel.onDoneChange(it, isChecked) }
            },
            colors =  CheckboxDefaults.colors(
                checkedColor = Color(schedule.color),
                uncheckedColor = Color(schedule.color),
                checkmarkColor = NeutralWhite
            ))
        Spacer(modifier = Modifier.fillMaxWidth(0.02f))
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(80.dp),
            elevation = 3.dp,
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            onClick = {navController.navigate("scheduleDetail/${schedule.scheduleId}")},
            backgroundColor = (
                    if (schedule.isDone) {
                        NeutralLight
                    } else NeutralLightness)
        ) {
            Row() {
                Box(modifier = Modifier
                    .width(10.dp)
                    .fillMaxHeight()
                    .background(color = Color(schedule.color))
                )
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 20.dp)
                    .background(
                        color = (
                            if (schedule.isDone) {
                                 NeutralLight
                            } else NeutralLightness)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = if (schedule.title.isEmpty()) "제목 없음" else "${schedule.title}",
                            style = Typography.h6,
                            color = (
                                    if (schedule.isDone) {
                                        NeutralMetal
                                    } else PrimaryDark)
                        )
                        Text(
                            text = "${schedule.startTime}",
                            style = Typography.body2,
                            color = NeutralGray
                        )

                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "${schedule.description}",
                            style = Typography.overline
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}