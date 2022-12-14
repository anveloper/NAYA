package com.youme.naya.schedule.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chargemap.compose.numberpicker.AMPMHours
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.chargemap.compose.numberpicker.ListItemPicker
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.*

// 시간 / 알림 설정
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleCreateSecond(
    viewModel: ScheduleMainViewModel = hiltViewModel()
){
    Column(
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .verticalScroll(rememberScrollState()),
        content = {
            // 시작 시간 선택
            var pickerStartValue by remember { mutableStateOf<Hours>(AMPMHours(1, 0, AMPMHours.DayTime.PM )) }
            var pickerStartString = pickerStartValue.toString().reversed()

            fun StringConverter (start: String, end: String, AMPM: String) : String {
                var start = start
                var end = end
                if (start.length == 1) { start = "0$start"}
                if (end.length == 1) { end = "0$end"}
                return "$start : $end $AMPM"
            }

            var showPickerStartDate by remember {
                mutableStateOf(false)
            }

            var showAlarmSetting by remember {
                mutableStateOf(false)
            }


            // 끝나는 시간
            var pickerEndValue by remember { mutableStateOf<Hours>(AMPMHours(12, 0, AMPMHours.DayTime.PM )) }
            var pickerEndString = pickerEndValue.toString().reversed()

            var showPickerEndDate by remember {
                mutableStateOf(false)
            }

            var showErrors by remember {
                mutableStateOf(false)
            }

            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                Text("시간 설정",
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = PrimaryDark,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { showPickerStartDate = !showPickerStartDate }),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("시작 시간",
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = NeutralGray,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                    Text(
                        viewModel.startTime.value,
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = PrimaryDark,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                    )

                }
            }
            if (showPickerStartDate) {
                Column(modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 8.dp, vertical = 10.dp)) {
                    HoursNumberPicker(
                        dividersColor = SecondaryLightBlue,
                        value = pickerStartValue,
                        onValueChange = {
                            pickerStartValue = it
                            pickerStartString = it.toString().reversed()
                            viewModel.onStartTimeChange(StringConverter(it.hours.toString(), it.minutes.toString(), pickerStartString.substring(1,3).reversed()))
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
                .fillMaxWidth(0.8f)
                .clickable(onClick = { showPickerEndDate = !showPickerEndDate }),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text("종료 시간",
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = NeutralGray,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
                Text(
                    viewModel.endTime.value,
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = PrimaryDark,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                )

            }
            if (showErrors) {
                Text("종료 시간을 제대로 설정해주세요", style = Typography.body2, color = SystemRed,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Center)
            }
            if (showPickerEndDate) {
                Column(modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 8.dp, vertical = 10.dp)) {
                    HoursNumberPicker(
                        dividersColor = SecondaryLightBlue,
                        value = pickerEndValue,
                        onValueChange = {
                            pickerEndValue = it
                            pickerEndString = it.toString().reversed()
                            var AMPM = pickerEndString.substring(1,3).reversed()
                            var hour = it.hours.toString()
                            var minute = it.minutes.toString()
                            if (AMPM > viewModel.startTime.value.substring(8, 10)) {
                                viewModel.onEndTimeChange(StringConverter(it.hours.toString(), it.minutes.toString(), pickerEndString.substring(1,3).reversed()))
                                showErrors = false
                            } else if (AMPM == viewModel.startTime.value.substring(8, 10)) {
                                if (hour.toInt() > viewModel.startTime.value.substring(0,2).toInt()) {
                                    viewModel.onEndTimeChange(StringConverter(it.hours.toString(),
                                        it.minutes.toString(),
                                        pickerEndString.substring(1, 3).reversed()))
                                    showErrors = false
                                } else if (hour.toInt()  == viewModel.startTime.value.substring(0,2).toInt() ) {
                                    if (minute.toInt()  >= viewModel.startTime.value.substring(5,7).toInt() ) {
                                        viewModel.onEndTimeChange(StringConverter(it.hours.toString(), it.minutes.toString(), pickerEndString.substring(1,3).reversed()))
                                        showErrors = false
                                    } else {
                                        showErrors = true
                                    }

                                    } else {
                                        showErrors = true
                                    }
                                }
                            else {
                                showErrors = true
                            }

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
            Divider(modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 20.dp), color = PrimaryLight)
            Row(modifier = Modifier
                .fillMaxWidth(0.8f)
                .clickable(onClick = { showPickerEndDate = !showPickerEndDate }),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text("일정 알람",
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = NeutralGray,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
                Switch(
                    checked = viewModel.isOnAlarm.value,
                    onCheckedChange = { viewModel.onAlarmChange() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = NeutralWhite,
                        checkedTrackColor = PrimaryBlue,
                        uncheckedThumbColor = NeutralWhite,
                        uncheckedTrackColor = NeutralLightness
                    )
                )

            }

            if (viewModel.isOnAlarm.value) {
                val possibleValues = listOf("시작 시간", "1시간 전", "3시간 전", "종료 시간")
                var state by remember { mutableStateOf(possibleValues[0]) }
                Row(modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clickable(onClick = { showAlarmSetting = !showAlarmSetting }),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("알람 시간 설정",
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = NeutralGray,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                    Text(
                        viewModel.alarmTime.value,
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = PrimaryDark,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                    )

                }
                if (showAlarmSetting) {
                    Column(modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ListItemPicker(
                            dividersColor = SecondaryLightBlue,
                            label = { it },
                            value = viewModel.alarmTime.value,
                            onValueChange = { viewModel.alarmTimeChange(it) },
                            list = possibleValues
                        )

                    }
                }
            }


        })
}

