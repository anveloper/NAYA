package com.youme.naya.schedule

sealed class Screen(val route: String) {
    object ScheduleEdit: Screen("scheduleEdit/{scheduleId}") {
        fun passId(scheduleId: Int?): String {
            return "scheduleEdit/$scheduleId"
        }
    }
    object ScheduleDetail: Screen("scheduleDetail/{scheduleId}") {
        fun passId(scheduleId: Int?): String {
            return "scheduleDetail/$scheduleId"
        }
    }
}
