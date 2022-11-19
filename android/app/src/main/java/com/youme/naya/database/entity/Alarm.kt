package com.youme.naya.database.entity

import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.youme.naya.ui.theme.SecondarySystemBlue
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

@Entity(tableName = "alarm")
data class Alarm(
    @PrimaryKey(autoGenerate = true) val alarmId: Int?,
    val title: String,
    var content: String,
    val date: String = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString(),
    val color: Int = SecondarySystemBlue.toArgb(),
    val isChecked: Boolean = false,
    val alarmTime: String,
)