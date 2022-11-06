package com.youme.naya.widgets.calendar.customCalendar.ui.fold.data

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
internal fun LocalDate.getNext7Dates(): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    val minusNum = this.dayOfWeek.value - 1
    val plusNum = 7 - minusNum
    repeat(minusNum) { day ->
        dates.add(this.minus(day.plus(1), DateTimeUnit.DAY))
    }
    repeat(plusNum) { day ->
        dates.add(this.plus(day, DateTimeUnit.DAY))
    }
    return dates.sorted()
}

@RequiresApi(Build.VERSION_CODES.O)
internal fun LocalDate.getPrevious7Dates(): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    val minusNum = this.dayOfWeek.value - 1
    repeat(7) { day ->
        dates.add(this.minus(day.plus(1 - minusNum), DateTimeUnit.DAY))
    }
    return dates.sorted()
}
