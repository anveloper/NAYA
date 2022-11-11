package com.youme.naya.screens.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.youme.naya.R
import com.youme.naya.components.OutlinedTinySmallButton
import com.youme.naya.components.PrimaryTinySmallButton
import com.youme.naya.schedule.ScheduleEditViewModel
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.*

private val CalendarHeaderBtnGroupModifier = Modifier
    .fillMaxWidth()
    .height(64.dp)
    .padding(start = 8.dp, end = 8.dp)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleDetailScreen(
    navController: NavController,
    scheduleId : Int,
    viewModel: ScheduleEditViewModel = hiltViewModel(),
    mainViewModel: ScheduleMainViewModel = hiltViewModel(),
) {

    Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            CalendarHeaderBtnGroupModifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TopAppBar(
                modifier = Modifier.height(64.dp),
                backgroundColor = NeutralWhite,
                elevation = 0.dp,
                contentPadding = PaddingValues(horizontal = 8.dp),
            ) {
                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Image(
                                painter = painterResource(R.drawable.ic_baseline_arrow_back_ios_24),
                                contentDescription = "Prev page button",
                                colorFilter = ColorFilter.tint(NeutralLight)
                            )
                        }
                        Text(
                            "수정",
                            fontFamily = fonts,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PrimaryBlue,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .clickable(onClick = {
                                    navController.navigate("scheduleEdit/${scheduleId}")
                                })
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier.width(300.dp)) {
            Row (
                modifier = Modifier.width(300.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box( modifier = Modifier
                        .width(16.dp)
                        .height(40.dp)
                        .background(color = Color(viewModel.color.value)))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column() {
                        Text(viewModel.title.value.text,
                            style=Typography.h4,
                            color = PrimaryDark)
                        Text(viewModel.currentSchedule?.scheduleDate.toString(),
                            style= Typography.body1,
                            color = NeutralGray)
                    }
                }

                if (viewModel.isDone.value) {
                    OutlinedTinySmallButton(text = "Undone", onClick = {
                        mainViewModel.onDoneChange(viewModel.currentSchedule!!, false)
                    })
                } else {
                    PrimaryTinySmallButton(text = "Done", onClick = {
                        mainViewModel.onDoneChange(
                        viewModel.currentSchedule!!, true)
                    })
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row (
                modifier = Modifier.width(300.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("시작 시간",
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = PrimaryDark,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,)
                Text(viewModel.currentSchedule?.startTime.toString(),
                    style= Typography.body1,
                    color = NeutralGray)
                }
            Spacer(modifier = Modifier.height(4.dp))
            Row (
                modifier = Modifier.width(300.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("종료 시간",
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = PrimaryDark,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,)
                Text(viewModel.currentSchedule?.endTime.toString(),
                    style= Typography.body1,
                    color = NeutralGray)
            }

            Text(viewModel.isOnAlarm.value.toString(),
                style= Typography.body1,
                color = NeutralGray)


            if (viewModel.isOnAlarm.value) {
                Divider(color = NeutralLightness)
                Row (
                    modifier = Modifier.width(300.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("알람 설정 시간",
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = PrimaryDark,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,)
                    Text(viewModel.currentSchedule?.alarmTime.toString(),
                        style= Typography.body1,
                        color = NeutralGray)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "주소",
                modifier = Modifier.padding(vertical = 12.dp),
                color = PrimaryDark,
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(viewModel.currentSchedule?.address.toString(),
                style= Typography.body1,
                color = NeutralGray)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "추가 기록 사항",
                modifier = Modifier.padding(vertical = 12.dp),
                color = PrimaryDark,
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(viewModel.description.value.text,
                style= Typography.body1,
                color = NeutralGray)

    }}}
