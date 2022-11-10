package com.youme.naya.schedule

sealed class Screen(val route: String) {
    object ScheduleEditScreen: Screen("scheduleEdit/{scheduleId}") {
        fun passId(scheduleId: Int?): String {
            return "scheduleEdit/$scheduleId"
        }
    }
    object ScheduleDetailScreen: Screen("scheduleDetail/{scheduleId}") {
        fun passId(scheduleId: Int?): String {
            return "scheduleDetail/$scheduleId"
        }
    }
}