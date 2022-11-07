package com.youme.naya.schedule

sealed class Screen(val route: String) {
    object ScheduleEdit: Screen("scheduleEdit?scheduleId={scheduleId}") {
        fun passId(scheduleId: Int?): String {
            return "edit?scheduleId=$scheduleId"
        }
    }
}
