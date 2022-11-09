package com.youme.naya.database.entity

import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.youme.naya.ui.theme.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

@Entity
data class Schedule(
    @PrimaryKey(autoGenerate = true) val scheduleId: Int? = null,
    val title: String,
    var scheduleDate: String = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString(),
    val color: Int = SecondarySystemBlue.toArgb(),
    val isOnAlarm: Boolean = false,
    val startTime: String ?= "00:00:AM",
    val endTime: String ?= "00:00:AM",
    val address: String ?= "",
    val description: String = "",
    val isDone: Boolean = false,
) {
    companion object {
        val scheduleColors = listOf(SystemPink, SystemOrange, SystemYellow, SystemGreen, SecondarySystemBlue, SystemPurple)
    }
}
