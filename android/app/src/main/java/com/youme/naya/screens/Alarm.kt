package com.youme.naya.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.youme.naya.R
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.schedule.AlarmViewModel
import com.youme.naya.ui.theme.*
@Composable
fun AlarmScreen(
    navController: NavHostController,
    viewModel: AlarmViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 28.dp)
    ) {
        Text("알림", color = PrimaryDark, style = Typography.h4)
        Spacer(modifier = Modifier.height(24.dp))
        if (viewModel.alarms.value.isNotEmpty()) {
            var date = viewModel.alarms.value.reversed()[0].alarmTime.substring(0,13)
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier
                    .width(100.dp)
                    .background(NeutralLightness)
                    .height(1.dp)
                )
                Text(date,
                    color = NeutralMedium,
                    style = Typography.body2,
                )
                Box(modifier = Modifier
                    .width(100.dp)
                    .background(NeutralLightness)
                    .height(1.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier.fillMaxHeight(0.8f),
            ) {
                items(viewModel.alarms.value.reversed()) { alarm ->
                    val alarmDate = alarm.alarmTime.substring(0,13)
                    val time = alarm.alarmTime.substring(14,21)
                    if (alarmDate != date) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier
                                .width(100.dp)
                                .background(NeutralLightness)
                                .height(1.dp)
                            )
                            Text(alarmDate,
                                color = NeutralMedium,
                                style = Typography.body2,
                            )
                            Box(modifier = Modifier
                                .width(100.dp)
                                .background(NeutralLightness)
                                .height(1.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        date = alarmDate
                    }
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.alarm_heart),
                            "alarm",
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp),
                            colorFilter = ColorFilter.tint(Color(alarm.color))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(alarm.title, style = Typography.body1, color = NeutralGray)
                        }
                        Text(time,
                            color = NeutralMedium,
                            style = Typography.body2
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row() {
                        Box(modifier = Modifier
                            .width(20.dp)
                            .height(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(alarm.content, style = Typography.body1, color = PrimaryDark)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
        else {
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(40.dp))
                Text("Alarm is FREE", fontFamily = pico, fontSize = 32.sp, color = PrimaryDark, modifier = Modifier.padding(vertical = 4.dp))
                Text("일정 알람을 이용해보세요!", color = NeutralMedium, style = Typography.body2)
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(R.drawable.icon_alarm_free),
                    "none_schedule",
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                )
            }

        }


    }

}