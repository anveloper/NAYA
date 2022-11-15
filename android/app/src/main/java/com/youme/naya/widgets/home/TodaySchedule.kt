package com.youme.naya.widgets.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.youme.naya.R
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.components.PrimarySmallButton
import com.youme.naya.components.PrimaryTinySmallButton
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.schedule.component.ScheduleItem
import com.youme.naya.schedule.component.ScheduleNone
import com.youme.naya.ui.theme.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodaySchedule(
    navController: NavHostController,
    viewModel: ScheduleMainViewModel = hiltViewModel(),
) {

    fun dateConvert(date: String) : String {
        return "${date.substring(5, 7)}월 ${date.substring(8, 10)}일 "
    }

    fun dateConvertTop(date: String) : String {
        return "${date.substring(0, 4)}년 ${date.substring(5, 7)}월 ${date.substring(8, 10)}일 "
    }

    Column(modifier = Modifier
        .background(
            color = NeutralWhite,
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            ),
        )
        .graphicsLayer {
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            )
            clip = true
        }
        .fillMaxSize()
        .shadow(
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            ),
            clip = true,
            elevation = 4.dp
        ),
    ) {
        Row(modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
                Column() {
                    Row( verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "오늘의 일정 ",
                            color = PrimaryDark,
                            style = Typography.h5
                        )
                        Image(
                            painter = painterResource(id = R.drawable.icon_calendar),
                            "calendar",
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(dateConvertTop(viewModel.selectedDate.value),
                        color = NeutralGray,
                        style = Typography.body2 )
                }
                PrimaryTinySmallButton(text = "달력 보기",
                    onClick = {navController.navigate("schedule")},
                    backgroundColor = PrimaryBlue,
                    contentColor = NeutralWhite
                )
            }

        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){
        if (viewModel.schedulesWithMembers.value.isNotEmpty()) {
            LazyColumn (
                state = rememberLazyListState(),
                modifier = Modifier.fillMaxHeight(0.8f).fillMaxWidth(0.88f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(viewModel.schedulesWithMembers.value) { schedule ->
                    ScheduleItem(
                        schedule = schedule,
                        navController = navController,
                    )
                }
            }
        } else {

                Spacer(modifier = Modifier.height(16.dp))
                ScheduleNone(
                    navController = navController,
                    selectedDate = dateConvert(viewModel.selectedDate.value))
                Spacer(modifier = Modifier.height(20.dp))
                PrimaryBigButton(text = "일정 등록하러 가기",
                    onClick = {
                        navController.navigate("scheduleCreate")
                    })
            }
        }
        Spacer(Modifier.height(72.dp))
    }
}
