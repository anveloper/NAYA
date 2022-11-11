package com.youme.naya.database.entity.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.youme.naya.database.entity.Member
import com.youme.naya.database.entity.Schedule

data class ScheduleWithMembers(
    @Embedded val schedule: Schedule,
    @Relation(
        parentColumn = "scheduleId",
        entityColumn = "scheduleId"
    )
    val members: List<Member>
)
