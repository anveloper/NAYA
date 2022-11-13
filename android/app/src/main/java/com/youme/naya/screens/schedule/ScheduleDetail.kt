package com.youme.naya.screens.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import com.youme.naya.components.PrimaryTinySmallButton
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
    viewModel: ScheduleMainViewModel = hiltViewModel(),
) {
    val done = if (viewModel.isDone.value) {
        remember { mutableStateOf(true) }
    } else {
        remember { mutableStateOf(false) }
    }

    Column (horizontalAlignment = Alignment.CenterHorizontally) {
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
                                }),

                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(0.88f)) {
            // title, 날짜, Done
            Row (
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box( modifier = Modifier
                        .fillMaxWidth(0.05f)
                        .fillMaxHeight()
                        .background(color = Color(viewModel.color.value)))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column() {
                        Text(viewModel.title.value.text.ifEmpty { "제목 없음" },
                            style=Typography.h4,
                            color = PrimaryDark)
                        Text(viewModel.selectedDate.value,
                            style= Typography.body1,
                            color = NeutralGray)
                    }
                }

                PrimaryTinySmallButton(text =
                    if (done.value) "UnDone"
                    else "Done",
                    onClick = {
                        viewModel.onDoneChange(
                            scheduleId, !viewModel.isDone.value)
                        done.value = !done.value
                    },
                    backgroundColor =
                    if (done.value) NeutralWhite
                    else PrimaryBlue,
                    contentColor =
                    if (done.value) PrimaryBlue
                    else NeutralWhite,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            
            // 시작시간
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("시작 시간",
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = PrimaryDark,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,)
                Text(viewModel.startTime.value,
                    style= Typography.body1,
                    color = NeutralGray)
                }
            Spacer(modifier = Modifier.height(4.dp))
            
            // 종료 시간
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("종료 시간",
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = PrimaryDark,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,)
                Text(viewModel.endTime.value,
                    style= Typography.body1,
                    color = NeutralGray)
            }
            // 알람 설정했으면, 표시
            if (viewModel.isOnAlarm.value) {
                Spacer(modifier = Modifier.height(4.dp))
                Divider(color = NeutralLightness)
                Spacer(modifier = Modifier.height(4.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("알람 설정 시간",
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = PrimaryDark,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,)
                    Text(viewModel.alarmTime.value,
                        style= Typography.body1,
                        color = NeutralGray)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            
            // 주소
            if (viewModel.address.value.text.isNotEmpty()) {
            Text(
                "주소",
                modifier = Modifier.padding(vertical = 12.dp),
                color = PrimaryDark,
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(viewModel.address.value.text,
                style= Typography.body1,
                color = NeutralGray)
            Spacer(modifier = Modifier.height(12.dp))
            }
            
            // 추가 기록 사항
            if (viewModel.description.value.text.isNotEmpty()) {
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
            }


    }}}
