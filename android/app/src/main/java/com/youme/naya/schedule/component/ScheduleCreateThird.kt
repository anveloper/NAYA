package com.youme.naya.schedule.component


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.youme.naya.R
import com.youme.naya.components.BasicTextField
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.PrimaryBlue
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.fonts

// 장소 등록
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScheduleCreateThird(
    viewModel: ScheduleMainViewModel = hiltViewModel()
){
    Column(
        modifier = Modifier
            .width(300.dp)
            .height(320.dp)
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

