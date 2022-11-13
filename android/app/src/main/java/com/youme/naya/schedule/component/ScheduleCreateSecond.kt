package com.youme.naya.schedule.component

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
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

// 시간 / 알림 설정
@Composable
fun ScheduleCreateSecond(
    viewModel: ScheduleMainViewModel = hiltViewModel()
){
    Column(
        modifier = Modifier
            .width(300.dp)
            .height(320.dp)
            .verticalScroll(rememberScrollState()),
        content = {
            // 시작 시간 선택
            var pickerStartValue by remember { mutableStateOf<Hours>(AMPMHours(0, 0, AMPMHours.DayTime.PM )) }
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
                    .width(300.dp)
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
                .width(300.dp)
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
            if (showPickerEndDate) {
                Column(modifier = Modifier
                    .width(300.dp)
                    .padding(horizontal = 8.dp, vertical = 10.dp)) {
                    HoursNumberPicker(
                        dividersColor = SecondaryLightBlue,
                        value = pickerEndValue,
                        onValueChange = {
                            pickerEndValue = it
                            pickerEndString = it.toString().reversed()
                            viewModel.onEndTimeChange(StringConverter(it.hours.toString(), it.minutes.toString(), pickerEndString.substring(1,3).reversed()))
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
            Divider(modifier = Modifier.width(300.dp).padding(vertical = 20.dp), color = PrimaryLight)
            Row(modifier = Modifier
                .width(300.dp)
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
                    .width(300.dp)
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
                        .width(300.dp)
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

