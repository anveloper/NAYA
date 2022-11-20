package com.youme.naya.schedule.component

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.youme.naya.components.BasicTextField
import com.youme.naya.network.RetrofitClient
import com.youme.naya.network.RetrofitService
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.*
import com.youme.naya.vo.MapResponseVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// 장소 등록
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScheduleCreateThird(
    viewModel: ScheduleMainViewModel = hiltViewModel()
) {
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
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .verticalScroll(rememberScrollState()),
        content = {
    val keyboardController = LocalSoftwareKeyboardController.current
    // focus
    val focusRequester = remember { FocusRequester() }

            Text(
                "장소 검색",
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
                trailingIcon = {
                    Image(
                    painter = painterResource(id = com.youme.naya.R.drawable.home_icon_search),
                        "setting",
                        colorFilter = ColorFilter.tint(PrimaryBlue)
                    )}
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
                        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
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
                            modifier = Modifier.width(50.dp).height(50.dp)
                        )
                        Text(text = "도로명 주소를 입력해보세요. \n " +
                                "해당 주소의 위치를 지도로 만나볼 수 있어요!", color = NeutralGray, style = Typography.h6,
                            textAlign = TextAlign.Center
                        )

                    }

                }

            }

        }
    )

}
