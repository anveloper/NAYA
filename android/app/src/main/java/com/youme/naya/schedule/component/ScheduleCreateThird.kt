package com.youme.naya.schedule.component


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.youme.naya.components.BasicTextField
import com.youme.naya.network.RetrofitClient
import com.youme.naya.network.RetrofitService
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.fonts
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
){
    var retrofit = RetrofitClient.getInstance()
    var supplementService = retrofit.create(RetrofitService::class.java)
    Column(
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .verticalScroll(rememberScrollState()),
        content = {
    val keyboardController = LocalSoftwareKeyboardController.current
    // focus
    val focusRequester = remember { FocusRequester() }

    Text("장소 검색",
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
        text = viewModel.address.value.text,
        onChange = { viewModel.onAddressChange(it) },
        placeholder = "주소 입력",
        imeAction = ImeAction.Done,
        keyBoardActions = KeyboardActions(onDone = {
            supplementService.map(viewModel.address.value.text).enqueue(object : Callback<MapResponseVO> {
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
                    Log.i("x",response.body()?.x.toString())
                    Log.i("y",response.body()?.y.toString())
                    Log.i("jibun",response.body()?.jibunAddress.toString())
                    Log.i("road",response.body()?.roadAddress.toString())
                }
            })
            keyboardController?.hide()
        }),
//        trailingIcon = {
//            Image(
//            painter = painterResource(R.drawable.home_icon_search),
//                "setting",
//                colorFilter = ColorFilter.tint(PrimaryBlue)
//        )}
    )
})}
