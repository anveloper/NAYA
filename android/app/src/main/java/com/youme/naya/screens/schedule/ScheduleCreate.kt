package com.youme.naya.screens.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.chargemap.compose.numberpicker.AMPMHours
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.youme.naya.R
import com.youme.naya.components.BasicTextField
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.database.entity.Schedule
import com.youme.naya.schedule.edit.ScheduleEditViewModel
import com.youme.naya.ui.theme.*
import com.youme.naya.widgets.calendar.AnimatedCalendar
import com.youme.naya.widgets.calendar.CalendarViewModel
import com.youme.naya.widgets.calendar.scheduleCreate.*
import com.youme.naya.widgets.calendar.scheduleCreate.component.*
import com.youme.naya.widgets.common.HeaderBar

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleCreateScreen(
    navController: NavHostController,
    viewModel: ScheduleEditViewModel = hiltViewModel()
) {
    val componentVariable = remember {
        mutableStateOf(0)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    var title by rememberSaveable {
        mutableStateOf("")
    }

    var etc by rememberSaveable {
        mutableStateOf("")
    }

    var selectedColor by rememberSaveable {
        mutableStateOf(SecondarySystemBlue.toArgb())
    }

    // focus
    val focusRequester = remember { FocusRequester() }

    var pickerValue by remember { mutableStateOf<Hours>(AMPMHours(0, 0, AMPMHours.DayTime.PM )) }
    var pickerString = pickerValue.toString().reversed()
    var pickerDate by remember {
        mutableStateOf(
            pickerValue.hours.toString() + " : " + pickerValue.minutes.toString() + " "
                    + pickerString.substring(1,3).reversed()) }

    var showPickerDate by remember {
        mutableStateOf(false)
    }

    var pickerValue2 by remember { mutableStateOf<Hours>(AMPMHours(12, 0, AMPMHours.DayTime.PM )) }
    var pickerString2 = pickerValue.toString().reversed()
    var pickerDate2 by remember {
        mutableStateOf(
            pickerValue2.hours.toString() + " : " + pickerValue2.minutes.toString() + " "
                    + pickerString2.substring(1,3).reversed()) }

    var showPickerDate2 by remember {
        mutableStateOf(false)
    }

    val calendarViewModel = viewModel<CalendarViewModel>()
    var selectedDate by remember { mutableStateOf(calendarViewModel.selectedDate) }


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
            takeMeToDate = selectedDate.value
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
                    0 -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween

                        ) {
                            Schedule.scheduleColors.forEach { color ->
                                val colorInt = color.toArgb()
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(color.copy(alpha = if (selectedColor == colorInt) 1f else 0.3f))
                                        .clickable(onClick = { selectedColor = colorInt })
                                )

                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Column {
                            Text("일정명",
                                modifier = Modifier.padding(vertical = 12.dp),
                                color = PrimaryDark,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            BasicTextField(
                                modifier = Modifier
                                    .focusRequester(focusRequester),
                                text = title,
                                onChange = { title = it },
                                placeholder = "일정명 입력",
                                imeAction = ImeAction.Done,
                                keyBoardActions = KeyboardActions(onDone = {
                                    keyboardController?.hide()
                                })
                            )
                        }
                    }
                    1 -> {
                        Column(modifier = Modifier.width(300.dp)) {
                            Text("시간 설정",
                                modifier = Modifier.padding(vertical = 12.dp),
                                color = PrimaryDark,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Row(modifier = Modifier
                                .width(300.dp)
                                .clickable(onClick = { showPickerDate = !showPickerDate }),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("시작 시간",
                                    modifier = Modifier.padding(vertical = 12.dp),
                                    color = NeutralGray,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp
                                )
                                Text(
                                    pickerDate,
                                    modifier = Modifier.padding(vertical = 12.dp),
                                    color = PrimaryDark,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp,
                                )

                            }}
                        if (showPickerDate) {
                            Column(modifier = Modifier.width(300.dp).padding(horizontal = 8.dp, vertical = 10.dp)) {
                                HoursNumberPicker(
                                    dividersColor = SecondaryLightBlue,
                                    value = pickerValue,
                                    onValueChange = {
                                        pickerValue = it
                                        pickerString = it.toString().reversed()
                                        pickerDate = it.hours.toString() + " : " + it.minutes.toString() + " " +
                                                pickerString.substring(1,3).reversed()
                                    },
                                    hoursDivider = {
                                        Text(
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp),
                                            textAlign = TextAlign.Center,
                                            text = ":"
                                        )
                                    },
                                    minutesDivider = {
                                        Text(
                                            modifier = Modifier.padding(horizontal = 8.dp),
                                            textAlign = TextAlign.Center,
                                            text = " "
                                        )
                                    })

                            }
                        }
                        Row(modifier = Modifier
                            .width(300.dp)
                            .clickable(onClick = { showPickerDate2 = !showPickerDate2 }),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("종료 시간",
                                modifier = Modifier.padding(vertical = 12.dp),
                                color = NeutralGray,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                            Text(
                                pickerDate2,
                                modifier = Modifier.padding(vertical = 12.dp),
                                color = PrimaryDark,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp,
                            )

                        }
                    if (showPickerDate2) {
                        Column(modifier = Modifier.width(300.dp).padding(horizontal = 8.dp, vertical = 10.dp)) {
                            HoursNumberPicker(
                                dividersColor = SecondaryLightBlue,
                                value = pickerValue2,
                                onValueChange = {
                                    pickerValue2 = it
                                    pickerString2 = it.toString().reversed()
                                    pickerDate2 = it.hours.toString() + " : " + it.minutes.toString() + " " +
                                            pickerString2.substring(1,3).reversed()
                                },
                                hoursDivider = {
                                    Text(
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp),
                                        textAlign = TextAlign.Center,
                                        text = ":"
                                    )
                                },
                                minutesDivider = {
                                    Text(
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        textAlign = TextAlign.Center,
                                        text = " "
                                    )
                                })

                        }
                    }}
                    2 -> Text("장소 검색",
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = PrimaryDark,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    3 -> {
                        Text("멤버 등록",
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = PrimaryDark,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(
                            painter = painterResource(R.drawable.schedule_member_register_icon),
                            contentDescription = "member_plus",
                            modifier = Modifier
                                .width(64.dp)
                                .height(64.dp),
                        )
                    }
                    4 -> Column (modifier = Modifier.width(300.dp)) {
                        Text("추가 기록 사항",
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = PrimaryDark,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        BasicTextField(
                            modifier = Modifier
                                .focusRequester(focusRequester),
                            text = etc,
                            onChange = { etc = it },
                            placeholder = "추가 기록 사항 입력",
                            imeAction = ImeAction.Done,
                            keyBoardActions = KeyboardActions(onDone = {
                                keyboardController?.hide()
                            })
                        )
                    }
                }
                })

            PrimaryBigButton(
                text = when (componentVariable.value) {
                    4 -> "등록하기"
                    else -> "다음"
                },
                onClick = {
                    componentVariable.value = componentVariable.value + 1
                    when (componentVariable.value) {
                        0 -> { if (title == "") title = "제목 없음" else title = title}
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


