package com.youme.naya.screens.schedule

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.youme.naya.R
import com.youme.naya.components.PrimaryTinySmallButton
import com.youme.naya.database.entity.Member
import com.youme.naya.database.entity.Member.Companion.memberIconsColor
import com.youme.naya.network.RetrofitClient
import com.youme.naya.network.RetrofitService
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.*
import com.youme.naya.vo.MapResponseVO
import com.youme.naya.widgets.items.ImageContainer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private val CalendarHeaderBtnGroupModifier = Modifier
    .fillMaxWidth()
    .height(64.dp)
    .padding(start = 8.dp, end = 8.dp)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleDetailScreen(
    navController: NavController,
    scheduleId: Int,
    viewModel: ScheduleMainViewModel = hiltViewModel(),
) {
    val done = if (viewModel.isDone.value) {
        remember { mutableStateOf(true) }
    } else {
        remember { mutableStateOf(false) }
    }

    var detailOpen = remember { mutableStateOf(false) }

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


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
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
                Row(
                    Modifier.fillMaxWidth(),
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
            modifier = Modifier.width(300.dp)
        ) {
            // title, 날짜, Done
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.05f)
                            .fillMaxHeight()
                            .background(color = Color(viewModel.color.value))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column() {
                        Text(
                            viewModel.title.value.text.ifEmpty { "제목 없음" },
                            style = Typography.h4,
                            color = PrimaryDark
                        )
                        Text(
                            viewModel.selectedDate.value,
                            style = Typography.body1,
                            color = NeutralGray
                        )
                    }
                }

                PrimaryTinySmallButton(
                    text =
                    if (done.value) "UnDone"
                    else "Done",
                    onClick = {
                        viewModel.onDoneChange(
                            scheduleId, !viewModel.isDone.value
                        )
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "시작 시간",
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = PrimaryDark,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Text(
                    viewModel.startTime.value,
                    style = Typography.body1,
                    color = NeutralGray
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            // 종료 시간
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "종료 시간",
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = PrimaryDark,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Text(
                    viewModel.endTime.value,
                    style = Typography.body1,
                    color = NeutralGray
                )
            }
            // 알람 설정했으면, 표시
            if (viewModel.isOnAlarm.value) {
                Spacer(modifier = Modifier.height(4.dp))
                Divider(color = NeutralLightness)
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "알람 설정 시간",
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = PrimaryDark,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                    Text(
                        viewModel.alarmTime.value,
                        style = Typography.body1,
                        color = NeutralGray
                    )
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
                Text(
                    viewModel.address.value.text,
                    style = Typography.body1,
                    color = NeutralGray
                )
                Spacer(modifier = Modifier.height(12.dp))
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

                if (mapShow.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
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
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }

            }

            if (viewModel.memberList.value.isNotEmpty()) {
                var temporaryMember:
                        MutableState<Member> =
                    remember { mutableStateOf(viewModel.memberList.value[0]) }

                Text(
                    "함께 하는 멤버",
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = PrimaryDark,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyVerticalGrid(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth(0.88f),
                    columns = GridCells.Fixed(5),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                )
                {
                    items(viewModel.memberList.value.size) { index ->
                        Row() {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                if (detailOpen.value && viewModel.memberList.value[index] == temporaryMember.value) {
                                    Image(
                                        painter = painterResource(Member.memberIconsFocus[viewModel.memberList.value[index].memberIcon!!]),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .width(60.dp)
                                            .height(60.dp)
                                            .clickable(onClick = {
                                                detailOpen.value = !detailOpen.value
                                                temporaryMember.value =
                                                    viewModel.memberList.value[index]
                                            }),
                                    )
                                } else if (detailOpen.value && viewModel.memberList.value[index] != temporaryMember.value) {
                                    Image(
                                        painter = painterResource(Member.memberIcons[viewModel.memberList.value[index].memberIcon!!]),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .width(60.dp)
                                            .height(60.dp)
                                            .clickable(onClick = {
                                                detailOpen.value = detailOpen.value
                                                temporaryMember.value =
                                                    viewModel.memberList.value[index]
                                            }),
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(Member.memberIcons[viewModel.memberList.value[index].memberIcon!!]),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .width(60.dp)
                                            .height(60.dp)
                                            .clickable(onClick = {
                                                detailOpen.value = !detailOpen.value
                                                temporaryMember.value =
                                                    viewModel.memberList.value[index]
                                            }),
                                    )
                                }

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
                                    .height(20.dp)
                            )
                        }

                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (detailOpen.value) {

                        Box(
                            modifier = Modifier
                                .width(
                                    if (temporaryMember.value.type == 0 ||
                                        (temporaryMember.value.type == 1 && temporaryMember.value.nuyaType == 1)
                                    ) 240.dp else 150.dp
                                )
                                .height(
                                    if (temporaryMember.value.type == 0 ||
                                        (temporaryMember.value.type == 1 && temporaryMember.value.nuyaType == 1)
                                    ) 150.dp else 240.dp
                                )
                                .background(PrimaryLight)
                                .shadow(elevation = 1.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            if (temporaryMember.value.type == 0) {
                                Box(
                                    modifier = Modifier
                                        .width(220.dp)
                                        .height(130.dp)
                                        .border(
                                            width = 2.dp,
                                            color = memberIconsColor[temporaryMember.value.memberIcon!!]
                                        ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Column() {
                                        temporaryMember.value.name?.let {
                                            Text(it, color = PrimaryDark, style = Typography.h6)
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        temporaryMember.value.phoneNum?.let {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(0.8f),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    "전화번호",
                                                    color = NeutralGray,
                                                    style = Typography.body2
                                                )
                                                Text(
                                                    it,
                                                    color = PrimaryDark,
                                                    style = Typography.body2
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        temporaryMember.value.email?.let {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(0.8f),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    "이메일",
                                                    color = NeutralGray,
                                                    style = Typography.body2
                                                )
                                                Text(
                                                    it,
                                                    color = PrimaryDark,
                                                    style = Typography.body2
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                        }
                                        temporaryMember.value.etcInfo?.let {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(0.8f),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    "추가 정보",
                                                    color = NeutralGray,
                                                    style = Typography.body2
                                                )
                                                Text(
                                                    it,
                                                    color = PrimaryDark,
                                                    style = Typography.body2
                                                )
                                            }
                                        }
                                    }

                                }
                            } else {
                                if (temporaryMember.value.nuyaType == 0) {
                                    ImageContainer(
                                        Uri.parse(temporaryMember.value.cardUri)
                                    )
                                } else {
                                    stringToBitmap(temporaryMember.value.cardUri)?.let {
                                        ImageContainer(
                                            it
                                        )
                                    }
                                }


                            }
                        }
                    }
                }
            }
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
                items(viewModel.memberList.value.size) { index ->
                    Row() {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(Member.memberIcons[viewModel.memberList.value[index].memberIcon!!]),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp)
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
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

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
            Text(
                viewModel.description.value.text,
                style = Typography.body1,
                color = NeutralGray
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}


/*
     * String형을 BitMap으로 변환시켜주는 함수
     * */
fun stringToBitmap(encodedString: String?): Bitmap? {
    return try {
        val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    } catch (e: Exception) {
        e.message
        null
    }
}