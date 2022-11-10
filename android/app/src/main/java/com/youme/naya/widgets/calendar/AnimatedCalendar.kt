package com.youme.naya.widgets.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.NeutralWhite
import com.youme.naya.widgets.calendar.customCalendar.CustomCalendar
import com.youme.naya.widgets.calendar.customCalendar.component.day.config.CustomCalendarDayColors
import com.youme.naya.widgets.calendar.customCalendar.component.day.config.CustomCalendarDayDefaultColors
import com.youme.naya.widgets.calendar.customCalendar.component.header.config.CustomCalendarHeaderConfig
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarDay
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarEvent
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarType
import com.youme.naya.widgets.calendar.customCalendar.ui.basic.CustomCalendarColors
import com.youme.naya.widgets.calendar.customCalendar.ui.basic.CustomCalendarThemeColor
import kotlinx.datetime.LocalDate

@Composable
private fun BottomShadow(alpha: Float = 0.1f, height: Dp = 8.dp) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(height)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Black.copy(alpha = alpha),
                    Color.Transparent,
                )
            )
        )
    )
}

private val CornerShape =
    Modifier.clip(
        shape = RoundedCornerShape(
            bottomStartPercent = 10,
            bottomEndPercent = 10)
    )

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
fun AnimatedCalendar(
    expanded: Boolean,
    takeMeToDate: LocalDate,
    modifier: Modifier = Modifier,
    customCalendarEvents: List<CustomCalendarEvent>,
    customCalendarThemeColors: List<CustomCalendarThemeColor> = CustomCalendarColors.defaultColors(),
    onCurrentDayClick: (CustomCalendarDay, List<CustomCalendarEvent>) -> Unit = { _, _ -> },
    customCalendarDayColors: CustomCalendarDayColors = CustomCalendarDayDefaultColors.defaultColors(),
    customCalendarHeaderConfig: CustomCalendarHeaderConfig? = null,
    viewModel: ScheduleMainViewModel = hiltViewModel(),
) {

    var expanded by remember { mutableStateOf(expanded) }

    Surface (
        modifier = CornerShape,
        onClick = { expanded = !expanded }
    ) {
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(150,150)) with
                fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    // Expand horizontally first.
                                    IntSize(targetSize.width, initialSize.height) at 150
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    // Shrink vertically first.
                                    IntSize(initialSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }
            }
        )
        { targetExpended ->
            if (targetExpended) {
                BottomShadow(alpha = 1f, height = 460.dp)
                Box(modifier = CornerShape) {
                    Box( modifier = Modifier
                        .background(color = NeutralWhite)
                        .height(450.dp)
                        .fillMaxWidth()
                        .padding(top = 8.dp)) {
                    }
                }
            } else {
                BottomShadow(alpha = 1f, height = 190.dp)
                Box(modifier = CornerShape) {
                    Box( modifier = Modifier
                        .background(color = NeutralWhite)
                        .height(180.dp)
                        .fillMaxWidth()
                        .padding(top = 8.dp)) {
                    }
                }
            }
        }
        CustomCalendar(
            customCalendarType =
                if (expanded) CustomCalendarType.Basic
                else CustomCalendarType.Fold(true),
            modifier = modifier.wrapContentHeight(),
            customCalendarEvents = customCalendarEvents,
            onCurrentDayClick = onCurrentDayClick,
            customCalendarDayColors = customCalendarDayColors,
            customCalendarThemeColors = customCalendarThemeColors,
            customCalendarHeaderConfig = customCalendarHeaderConfig,
            takeMeToDate = takeMeToDate,
        )
    }
}

