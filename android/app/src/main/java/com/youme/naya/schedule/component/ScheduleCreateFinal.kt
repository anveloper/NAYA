package com.youme.naya.schedule.component

import android.os.Build
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
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.fonts

// 추가 기록 사항
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScheduleCreateFinal(
    viewModel: ScheduleMainViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .verticalScroll(rememberScrollState()),
        content = {
    val keyboardController = LocalSoftwareKeyboardController.current
    // focus
    val focusRequester = remember { FocusRequester() }

    Column {
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
            text = viewModel.description.value.text,
            onChange = { viewModel.onDescriptionChange(it) },
            placeholder = "추가 기록 사항 입력",
            imeAction = ImeAction.Done,
            keyBoardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            })
        )
    }
})}