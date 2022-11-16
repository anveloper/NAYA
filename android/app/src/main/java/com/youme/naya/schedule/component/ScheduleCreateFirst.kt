package com.youme.naya.schedule.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeAction.Companion.Done
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.youme.naya.components.BasicTextField
import com.youme.naya.database.entity.Schedule
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.fonts

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScheduleCreateFirst(
    viewModel: ScheduleMainViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .verticalScroll(rememberScrollState()),
        content = {

            val keyboardController = LocalSoftwareKeyboardController.current
            // focus
            val focusRequester = remember { FocusRequester() }

            Column( modifier = Modifier
                .fillMaxWidth(0.8f)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
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
        },
    )}