package com.youme.naya.database.entity

import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.youme.naya.ui.theme.*
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.util.*

@Entity
data class Schedule(
    @PrimaryKey(autoGenerate = true) var scheduleId: Int? = null,
    val title: String,
    var date: String ?= Clock.System.todayIn(TimeZone.currentSystemDefault()).toString(),
    val color: Int = SecondarySystemBlue.toArgb(),
    val isAllTime: Boolean = false,
    val startTime: String ?= null,
    val endTime: String ?= null,
    val address: String ?= null,
    val content: String ?= null,
    val isDone: Boolean = false,
) {
    companion object {
        val scheduleColors = listOf(SystemPink, SystemOrange, SystemYellow, SystemGreen, SecondarySystemBlue, SystemPurple)
    }
}

class InvalidScheduleException(message: String) : Exception(message)
