package com.youme.naya.schedule.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
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
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.fonts

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MemberInput(
    viewModel: ScheduleMainViewModel = hiltViewModel(),
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    // focus
    val focusRequester = remember { FocusRequester() }

    Spacer(modifier = Modifier.height(12.dp))
    Column() {
        Text(
            "이름",
            modifier = Modifier.padding(vertical = 12.dp),
            color = PrimaryDark,
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(2.dp))
        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth(0.88f)
            ,
            text = viewModel.memberName.value.text,
            onChange = { viewModel.onMemNameChange(it) },
            placeholder = "멤버 이름 입력",
            imeAction = ImeAction.Done,
            keyBoardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                },
            ),
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Column() {
    Text(
        "전화번호",
        modifier = Modifier.padding(vertical = 12.dp),
        color = PrimaryDark,
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    )
    Spacer(modifier = Modifier.height(2.dp))
    BasicTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth(0.88f)
        ,
        text = viewModel.memberPhone.value.text,
        onChange = { viewModel.onMemPhoneChange(it) },
        placeholder = "전화번호 입력",
        imeAction = ImeAction.Done,
        keyBoardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            },
        ),
    )}
    Spacer(modifier = Modifier.height(8.dp))
    Column() {
    Text(
        "이메일",
        modifier = Modifier.padding(vertical = 12.dp),
        color = PrimaryDark,
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    )
    Spacer(modifier = Modifier.height(4.dp))
    BasicTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth(0.88f)
        ,
        text = viewModel.memberEmail.value.text,
        onChange = { viewModel.onMemEmailChange(it) },
        placeholder = "이메일 입력",
        imeAction = ImeAction.Done,
        keyBoardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            },
        ),
    )}
    Spacer(modifier = Modifier.height(8.dp))
    Column() {
    Text(
        "추가 정보",
        modifier = Modifier.padding(vertical = 12.dp),
        color = PrimaryDark,
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    )
    Spacer(modifier = Modifier.height(2.dp))
    BasicTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth(0.88f)
        ,
        text = viewModel.memberEtcInfo.value.text,
        onChange = { viewModel.onMemEtcChange(it) },
        placeholder = "추가 정보 입력",
        imeAction = ImeAction.Done,
        keyBoardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            },
        ),
    )}
    Spacer(modifier = Modifier.height(32.dp))
}