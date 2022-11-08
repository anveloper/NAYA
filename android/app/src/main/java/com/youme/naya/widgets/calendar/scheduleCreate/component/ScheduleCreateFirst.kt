package com.youme.naya.widgets.calendar.scheduleCreate.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction.Companion.Done
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.components.BasicTextField
import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.repository.ScheduleRepository
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.fonts

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScheduleCreateFirst(
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable {
        mutableStateOf("")
    }

    // focus
    val focusRequester = remember { FocusRequester() }
    Row(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Schedule.scheduleColors.forEach { color ->
            val colorInt = color.toArgb()
            Box(
                modifier = androidx.compose.ui.Modifier
                    .size(40.dp)
                    .shadow(4.dp, CircleShape)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }

    Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
    Column() {
        Text("일정명",
            modifier = androidx.compose.ui.Modifier.padding(vertical = 12.dp),
            color = PrimaryDark,
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        BasicTextField(
            modifier = androidx.compose.ui.Modifier
                .focusRequester(focusRequester),
            text = text,
            onChange = { text = it },
            placeholder = "일정명 입력",
            imeAction = Done,
            keyBoardActions = KeyboardActions(onDone = {
//                viewModel.insertSchedule(text)
                text = ""
                keyboardController?.hide()
            })
        )

    }

}