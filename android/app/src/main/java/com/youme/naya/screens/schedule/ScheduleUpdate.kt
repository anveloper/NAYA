package com.youme.naya.screens.schedule

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chargemap.compose.numberpicker.AMPMHours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.chargemap.compose.numberpicker.ListItemPicker
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.youme.naya.R
import com.youme.naya.card.BusinessCardGridListForSchedule
import com.youme.naya.card.NayaCardGridListForSchedule
import com.youme.naya.components.*
import com.youme.naya.database.entity.Member
import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.network.RetrofitClient
import com.youme.naya.network.RetrofitService
import com.youme.naya.schedule.CustomAlertDialog
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.schedule.component.MemberInput
import com.youme.naya.ui.theme.*
import com.youme.naya.vo.MapResponseVO
import com.youme.naya.widgets.common.NayaBcardSwitchButtons
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val CalendarHeaderBtnGroupModifier = Modifier
    .fillMaxWidth()
    .height(64.dp)
    .padding(start = 8.dp, end = 8.dp)

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun ScheduleUpdateScreen(
    navController: NavHostController,
    viewModel: ScheduleMainViewModel = hiltViewModel(),
    scheduleId: Int,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    // focus
    val focusRequester = remember { FocusRequester() }
    val openDialog = remember { mutableStateOf(false)  }
    var memberList = remember { mutableStateOf(viewModel.memberList) }

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val memberType = remember {
        mutableStateOf(-1)
    }

    val memberNum = remember {
        mutableStateOf(viewModel.memberList.value.size)
    }

    val cardViewModel: CardViewModel = hiltViewModel()
    val context = LocalContext.current

    ModalBottomSheetLayout(
        sheetContent = {
            LazyColumn (
                horizontalAlignment=Alignment.CenterHorizontally
            ){
                when (memberType.value) {
                    -1 -> item {
                        Box(modifier = Modifier
                            .clickable(
                                onClick = {
                                    memberType.value = 1
                                })
                            .padding(vertical = 4.dp)
                            .fillMaxWidth()
                            .height(48.dp),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = "Nuya 보관함에서 가져오기",
                                color = PrimaryBlue,
                                style = Typography.body1,
                            )
                        }
                        Box(modifier = Modifier
                            .clickable(
                                onClick = {
                                    memberType.value = 0
                                })
                            .padding(vertical = 4.dp)
                            .fillMaxWidth()
                            .height(48.dp),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = "직접 입력",
                                color = PrimaryBlue,
                                style = Typography.body1,
                            )
                        }
                    }
                    0 -> item {
                        Column(modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            MemberInput()
                            RegisterButton(
                                text = "등록",
                                onClick = {
                                    viewModel.currentScheduleId?.let {
                                        viewModel.insertTemporaryMember(
                                            memberType.value,
                                            memberNum.value % 6,
                                            it)
                                    }

                                    memberNum.value += 1
                                    memberType.value = -1

                                    coroutineScope.launch {
                                        bottomSheetState.hide()
                                    }
                                },
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            RegisterButton(
                                text = "다른 방법으로 선택하기",
                                onClick = {
                                    memberType.value = -1
                                },
                                isPrimary = false,
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }

                    }
                    1 -> item {
                        Spacer(Modifier.height(20.dp))
                        Text(
                            text = "함께 할 Nuya를 선택해주세요",
                            color = PrimaryDark,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(Color.White)
                        ) {
                            Column(
                                Modifier.height(400.dp)
                            ) {
                                NayaBcardSwitchButtons(
                                    nayaTab = {
                                        NayaCardGridListForSchedule(context,
                                            navController,
                                            true)
                                    },
                                    bCardTab = {
                                        BusinessCardGridListForSchedule(context,
                                            navController, cardViewModel,
                                            true)
                                    },
                                    isNuya = true
                                )
                                if (viewModel.cardUri.value != "") {
                                    viewModel.currentScheduleId?.let {
                                        viewModel.insertTemporaryMember(memberType.value,
                                            memberNum.value % 6,
                                            it
                                        )
                                    }

                                    memberNum.value += 1
                                    memberNum.value = 1

                                    coroutineScope.launch {
                                        bottomSheetState.hide()
                                    }
                                }
                            }
                        }
                        PrimaryBigButton(text = "다른 방법으로 선택하기",
                            onClick = {
                                memberType.value = -1 })
                        Spacer(Modifier.height(20.dp))
                    }
                }
            }
        },
        sheetState = bottomSheetState,
        scrimColor = Color(0XCCFFFFFF),
    ) {

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
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
                                "일정 수정",
                                fontFamily = fonts,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PrimaryDark,
                            )
                            Text(
                                "  삭제",
                                fontFamily = fonts,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PrimaryBlue,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clickable(onClick = {
                                        openDialog.value = true
                                    })
                            )
                        }
                    }
                }
            }
            // 삭제 시 모달
            DeleteModal(visible = openDialog.value,
                onDismissRequest = { openDialog.value = false },
                scheduleId = scheduleId,
                onDelete = {
                    viewModel.deleteSchedule(scheduleId)
                    navController.navigate("schedule")
                })

            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    Schedule.scheduleColors.forEach { color ->
                        val colorInt = color.toArgb()
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color.copy(alpha = if (viewModel.color.value == colorInt) 1f else 0.3f))
                                .clickable(onClick = { viewModel.onColorChange(colorInt) }),
                        )

                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Column {
                    Text(
                        "일정명",
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = PrimaryDark,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    BasicTextField(
                        modifier = Modifier
                            .focusRequester(focusRequester),
                        text = viewModel.title.value.text,
                        onChange = { viewModel.onTitleChange(it) },
                        placeholder = "일정명 입력",
                        imeAction = ImeAction.Done,
                        keyBoardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            },
                        ),
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                // 시간 설정
                val startHour = viewModel.startTime.value.substring(0,2).toInt()
                val startMinute = viewModel.startTime.value.substring(5,7).toInt()
                val startAMPM = if (viewModel.startTime.value.substring(8, 10) == "AM") {
                    AMPMHours.DayTime.AM
                } else {
                    AMPMHours.DayTime.PM
                }

                val endHour = viewModel.endTime.value.substring(0,2).toInt()
                val endMinute = viewModel.endTime.value.substring(5,7).toInt()
                val endAMPM = if (viewModel.endTime.value.substring(8, 10) == "AM") {
                    AMPMHours.DayTime.AM
                } else {
                    AMPMHours.DayTime.PM
                }

                // 시작 시간 선택
                var pickerStartValue = AMPMHours(
                    hours = startHour,
                    minutes = startMinute,
                    dayTime = startAMPM)


                // 끝나는 시간
                var pickerEndValue = AMPMHours(hours = endHour,
                    minutes = endMinute,
                    dayTime = endAMPM)

                var pickerStartString = pickerStartValue.toString().reversed()

                fun stringConverter(start: String, end: String, AMPM: String): String {
                    var start = start
                    var end = end
                    if (start.length == 1) {
                        start = "0$start"
                    }
                    if (end.length == 1) {
                        end = "0$end"
                    }
                    return "$start : $end $AMPM"
                }

                var showPickerStartDate by remember {
                    mutableStateOf(false)
                }

                var showAlarmSetting by remember {
                    mutableStateOf(false)
                }


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
                                pickerStartValue = it as AMPMHours
                                pickerStartString = it.toString().reversed()
                                viewModel.onStartTimeChange(stringConverter(it.hours.toString(),
                                    it.minutes.toString(),
                                    pickerStartString.substring(1, 3).reversed()))
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
                                var AMPM = pickerEndString.substring(1,3).reversed()
                                var hour = it.hours.toString()
                                var minute = it.minutes.toString()
                                if (AMPM > viewModel.startTime.value.substring(8, 10)) {
                                    viewModel.onEndTimeChange(stringConverter(it.hours.toString(), it.minutes.toString(), pickerEndString.substring(1,3).reversed()))
                                    showErrors = false
                                } else if (AMPM == viewModel.startTime.value.substring(8, 10)) {
                                    if (hour.toInt() > viewModel.startTime.value.substring(0,2).toInt()) {
                                        viewModel.onEndTimeChange(stringConverter(it.hours.toString(),
                                            it.minutes.toString(),
                                            pickerEndString.substring(1, 3).reversed()))
                                        showErrors = false
                                    } else if (hour.toInt()  == viewModel.startTime.value.substring(0,2).toInt() ) {
                                        if (minute.toInt()  >= viewModel.startTime.value.substring(5,7).toInt() ) {
                                            viewModel.onEndTimeChange(stringConverter(it.hours.toString(), it.minutes.toString(), pickerEndString.substring(1,3).reversed()))
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
                Divider(modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 20.dp), color = PrimaryLight)
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

                var retrofit = RetrofitClient.getInstance()
                var supplementService = retrofit.create(RetrofitService::class.java)
                var place = remember {
                    mutableStateOf(LatLng(37.5666805, 126.9784147))
                }
                var cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(place.value, 15f)
                }
                var address = "";
                LaunchedEffect(key1 = place.value) {
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(place.value, 15f)
                }

                var mapShow = remember {
                    mutableStateOf(false)
                }

                supplementService.map(viewModel.address.value.text)
                    .enqueue(object : retrofit2.Callback<com.youme.naya.vo.MapResponseVO> {
                        override fun onFailure(
                            call: retrofit2.Call<com.youme.naya.vo.MapResponseVO>,
                            t: kotlin.Throwable
                        ) {
                            android.util.Log.d("TAG", "실패 : {$t}")
                        }

                        override fun onResponse(
                            call: retrofit2.Call<com.youme.naya.vo.MapResponseVO>,
                            response: retrofit2.Response<com.youme.naya.vo.MapResponseVO>
                        ) {
                            if (response.body()?.x.toString() != "" && response.body() != null) {
                                var x = response.body()!!.x
                                var y = response.body()!!.y
                                if (x != null && y != null)
                                    place.value =
                                        com.google.android.gms.maps.model.LatLng(y.toDouble(),
                                            x.toDouble())
                                mapShow.value = true
                            } else {
                                mapShow.value = false
                            }
                        }

                    })

                Spacer(modifier = Modifier.height(16.dp))
                // 주소 등록
                Column {
                    Text(
                        "주소 등록",
                        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
                        color = PrimaryDark,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text("도로명 주소를 입력하면, 해당 위치 정보와 지도가 연동됩니다.", style = Typography.body2, color = SystemRed)
                    Spacer(modifier = Modifier.height(12.dp))
                    BasicTextField(
                        modifier = Modifier
                            .focusRequester(focusRequester),
                        text = viewModel.address.value.text,
                        onChange = { viewModel.onAddressChange(it) },
                        placeholder = "주소 입력",
                        imeAction = ImeAction.Done,
                        keyBoardActions = KeyboardActions(onDone = {
                            supplementService.map(viewModel.address.value.text)
                                .enqueue(object : Callback<MapResponseVO> {
                                    override fun onFailure(
                                        call: Call<MapResponseVO>,
                                        t: Throwable
                                    ) {
                                        Log.d("TAG", "실패 : {$t}")
                                    }

                                    override fun onResponse(
                                        call: Call<MapResponseVO>,
                                        response: Response<MapResponseVO>
                                    ) {
                                        Log.i("x", response.body()?.x.toString())
                                        Log.i("y", response.body()?.y.toString())
                                        Log.i("jibun", response.body()?.jibunAddress.toString())
                                        Log.i("road", response.body()?.roadAddress.toString())
                                        if (response.body()?.x.toString() != "" && response.body() != null) {
                                            var x = response.body()!!.x
                                            var y = response.body()!!.y
                                            if (x != null && y != null)
                                                place.value = LatLng(y.toDouble(), x.toDouble())
//                                    cameraPositionState.position =
//                                        CameraPosition.fromLatLngZoom(place.value, 12f)
                                            mapShow.value = true
                                        } else {
                                            mapShow.value = false
                                        }
                                    }

                                })
                            keyboardController?.hide()
                        }),
                    )
                    Box(modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (mapShow.value) {
                            Column() {
                                Spacer(modifier = Modifier.height(20.dp))
                                GoogleMap(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(),
                                    cameraPositionState = cameraPositionState
                                ) {
                                    Marker(
                                        state = MarkerState(position = place.value),
                                        title = "place",
                                        snippet = "Marker in place"
                                    )
                                }
                            }

                        } else {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = com.youme.naya.R.drawable.icon_map),
                                    "map",
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(50.dp)
                                )
                                Text(text = "도로명 주소를 입력해보세요. \n " +
                                        "해당 주소의 위치를 지도로 만나볼 수 있어요!", color = NeutralGray, style = Typography.h6,
                                    textAlign = TextAlign.Center
                                )

                            }

                        }

                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    Text(
                        "함께 하는 멤버",
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = PrimaryDark,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text("멤버 클릭시 삭제가 가능합니다.", style = Typography.body2, color = SystemRed)
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(R.drawable.schedule_member_register_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp)
                            .clickable(
                                enabled = true,
                                onClick = {
                                    coroutineScope.launch {
                                        bottomSheetState.show()
                                    }
                                }
                            )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyVerticalGrid(
                        modifier = Modifier
                            .height(80.dp)
                            .width(300.dp),
                        columns = GridCells.Fixed(5),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    )
                    {
                        items(memberList.value.value.size) { index ->
                            Row() {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = painterResource(Member.memberIconsCancel[viewModel.memberList.value[index].memberIcon!!]),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .width(60.dp)
                                            .height(60.dp)
                                            .clickable(
                                                enabled = true,
                                                onClick = {
                                                    viewModel.deleteTemporaryMember(index)
                                                }
                                            )
                                    )
                                    viewModel.memberList.value[index].name?.let {
                                        Text(
                                            it,
                                            color = NeutralGray,
                                            style = Typography.overline
                                        )

                                    }
                                }
                                Box(
                                    Modifier
                                        .width(16.dp)
                                        .height(20.dp))
                            }
                        }
                    }}


                Spacer(modifier = Modifier.height(16.dp))
                Column() {
                    // 추가 기록 사항
                    Text(
                        "추가 기록 사항",
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
                        text = viewModel.description.value.text,
                        onChange = { viewModel.onDescriptionChange(it) },
                        placeholder = "추가 기록 사항 입력",
                        imeAction = ImeAction.Done,
                        keyBoardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                        })
                    )}
                Spacer(modifier = Modifier.height(80.dp))
                PrimaryBigButton(text = "수정하기",
                    onClick = {
                        viewModel.insertSchedule(
                            selectedDate = viewModel.selectedDate.value, scheduleId = null
                        )
                        navController.navigate("schedule")
                    })
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun DeleteModal(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    scheduleId: Int,
    onDelete: () -> Unit,
) {
    if (visible) {
        CustomAlertDialog(onDismissRequest = { onDismissRequest() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.55f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = NeutralWhite),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 24.dp)
                    ,
                    text = "삭제 시 복구가 불가능합니다 \n 정말 삭제하시겠습니까?" ,
                    style = Typography.h6,
                    color = PrimaryDark,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row() {
                    OutlinedSmallButton(text = "취소", onClick = {
                        onDismissRequest()})
                    Spacer(modifier = Modifier.width(14.dp))
                    PrimarySmallButton(text = "삭제", onClick = {
                        onDelete()
                        onDismissRequest()})
                }
            }
        }
    }
}

