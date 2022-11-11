package com.youme.naya.screens.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.youme.naya.R
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.*
import com.youme.naya.widgets.calendar.AnimatedCalendar
import com.youme.naya.widgets.common.HeaderBar
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import androidx.navigation.NavController
import com.youme.naya.schedule.ScheduleEditViewModel
import com.youme.naya.schedule.component.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleCreateScreen(
    navController: NavController,
    viewModel: ScheduleEditViewModel = hiltViewModel(),
    viewMainModel: ScheduleMainViewModel = hiltViewModel(),
) {
    val componentVariable = remember {
        mutableStateOf(0)
    }

    Column {
        // 상단 바
        when (componentVariable.value) {
            0 -> HeaderBar(navController = navController, title = "일정 등록")
            else -> {
                TopAppBar(
                    modifier = Modifier.height(64.dp),
                    backgroundColor = NeutralWhite,
                    elevation = 0.dp,
                    contentPadding = PaddingValues(horizontal = 8.dp),
                ) {
                    Row(
                        Modifier.height(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            componentVariable.value = componentVariable.value - 1
                        }) {
                            Image(
                                painter = painterResource(R.drawable.ic_baseline_arrow_back_ios_24),
                                contentDescription = "Prev page button",
                                colorFilter = ColorFilter.tint(NeutralLight)
                            )
                        }
                        Text(
                            "일정 등록",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 48.dp),
                            textAlign = TextAlign.Center,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PrimaryDark
                        )
                    }
                }
            }
        }

        // 캘린더
        AnimatedCalendar(
            false,
            takeMeToDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
            customCalendarEvents = emptyList()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 하위 컴포넌트들
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {


                when (componentVariable.value) {
                    0 -> ScheduleCreateFirst()
                    1 -> ScheduleCreateSecond()
                    2 -> ScheduleCreateThird()
//                    3 -> {
//                        Column(
//                            modifier = Modifier
//                                .width(300.dp)
//                                .fillMaxHeight(),
//                            content = {
//                                val bottomSheetState =
//                                    rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
//                                val coroutineScope = rememberCoroutineScope()
//                                ModalBottomSheetLayout(
//                                    sheetContent = {
//                                        LazyColumn {
//                                            item {
//                                                Box(modifier = Modifier
//                                                    .clickable {
//                                                        coroutineScope.launch {
//                                                            bottomSheetState.hide()
//                                                        }
//                                                    }
//                                                    .padding(vertical = 4.dp)
//                                                    .fillMaxWidth()
//                                                    .height(48.dp),
//                                                    contentAlignment = Alignment.Center) {
//                                                    Text(
//                                                        text = "Naya에서 가져오기",
//                                                        color = PrimaryBlue,
//                                                        style = Typography.body1,
//                                                    )
//                                                }
//                                                Box(modifier = Modifier
//                                                    .clickable {
//                                                        coroutineScope.launch {
//                                                            bottomSheetState.hide()
//                                                        }
//                                                    }
//                                                    .padding(vertical = 4.dp)
//                                                    .fillMaxWidth()
//                                                    .height(48.dp),
//                                                    contentAlignment = Alignment.Center) {
//                                                    Text(
//                                                        text = "전화번호부에서 가져오기",
//                                                        color = PrimaryBlue,
//                                                        style = Typography.body1,
//                                                    )
//                                                }
//                                                Box(modifier = Modifier
//                                                    .clickable {
//                                                        coroutineScope.launch {
//                                                            bottomSheetState.hide()
//                                                        }
//                                                    }
//                                                    .padding(vertical = 4.dp)
//                                                    .fillMaxWidth()
//                                                    .height(48.dp),
//                                                    contentAlignment = Alignment.Center) {
//                                                    Text(
//                                                        text = "직접 입력",
//                                                        color = PrimaryBlue,
//                                                        style = Typography.body1,
//                                                    )
//                                                }
//                                        }}},
//                                    sheetState = bottomSheetState,
//                                    scrimColor = Color(0XCCFFFFFF),
//                                ) {
//                                    Column {
//                                        Column(
//                                            modifier = Modifier
//                                                .width(300.dp).height(320.dp)
//                                        ) {
//                                            Text("멤버 등록",
//                                                modifier = Modifier.padding(vertical = 12.dp),
//                                                color = PrimaryDark,
//                                                fontFamily = fonts,
//                                                fontWeight = FontWeight.Bold,
//                                                fontSize = 16.sp
//                                            )
//                                            Spacer(modifier = Modifier.height(8.dp))
//                                            Image(
//                                                painter = painterResource(R.drawable.schedule_member_register_icon),
//                                                contentDescription = "",
//                                                modifier = Modifier
//                                                    .width(64.dp)
//                                                    .height(64.dp)
//                                                    .clickable(
//                                                        enabled = true,
//                                                        onClick = {
//                                                            coroutineScope.launch {
//                                                                bottomSheetState.show()
//                                                            }
//                                                        }
//                                                    )
//                                            )}
//                                        PrimaryBigButton(
//                                            text = "다음",
//                                            onClick = {
//                                                componentVariable.value = componentVariable.value + 1
//                                            },
//                                        ) } }
//                            })
//                    }
//                   임시로 4 -> 3
                    3 -> ScheduleCreateFinal()
                }
//             임시로 주석 처리
//                if (componentVariable.value != 3) {
                    PrimaryBigButton(
                        text = when (componentVariable.value) {
//                            임시로 4 -> 3
                            3 -> "등록하기"
                            else -> "다음"
                        },
                        onClick = {
                            componentVariable.value = componentVariable.value + 1
                            when (componentVariable.value) {
//                                임시로 5 -> 4
                                4 -> {
                                    viewModel.insertSchedule(selectedDate = viewMainModel.selectedDate.value)
                                    viewMainModel.getEventSchedule()
                                    navController.navigate("schedule")
                                }
                            }
                        },
                    )
//                }
        })
    }

}


