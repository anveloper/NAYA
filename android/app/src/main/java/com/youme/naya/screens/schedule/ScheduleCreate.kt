package com.youme.naya.screens.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.youme.naya.schedule.ScheduleEditViewModel
import com.youme.naya.schedule.component.*

@OptIn(ExperimentalComposeUiApi::class)
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

    var scheduleDate by remember { mutableStateOf( viewMainModel.selectedDate ) }

    val keyboardController = LocalSoftwareKeyboardController.current

    // focus
    val focusRequester = remember { FocusRequester() }



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
            takeMeToDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 하위 컴포넌트들
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {

            Column(modifier = Modifier
                .width(300.dp)
                .height(320.dp)
                .verticalScroll(rememberScrollState()),
                content = {
                when (componentVariable.value) {
                    0 -> ScheduleCreateFirst()
                    1 -> ScheduleCreateSecond()
                    2 -> ScheduleCreateThird()
                    3 -> ScheduleCreateFourth()
                    4 -> ScheduleCreateFinal()
                }})


            PrimaryBigButton(
                text = when (componentVariable.value) {
                    4 -> "등록하기"
                    else -> "다음"
                },
                onClick = {
                    componentVariable.value = componentVariable.value + 1
                    when (componentVariable.value) {
//                        0 -> { if (title == "") title = "제목 없음" else title = title}
                        5 -> {
//                            viewModel.insertSchedule(title, selectedColor)
                            navController.navigate("schedule")
                        }
                    }
                },
            )

        })
    }

}


