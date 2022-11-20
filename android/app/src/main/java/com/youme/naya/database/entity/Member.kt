package com.youme.naya.database.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.youme.naya.R
import com.youme.naya.ui.theme.*

@Entity(tableName = "member",
    foreignKeys = [
        ForeignKey(
            entity = Schedule::class,
            parentColumns = ["scheduleId"],
            childColumns =  ["scheduleId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Member(
    @PrimaryKey(autoGenerate = true) val memberId: Int?,
    @ColumnInfo(name = "scheduleId") val scheduleId: Int?,
    val name: String? = null,
    // 어디서 가져온 정보인지
    val type: Int? = -1,
    val phoneNum: String? = null,
    val email: String? = null,
    // 기타 기록 사항
    val etcInfo: String? = null,
    // Nuya 카드이면 너야 카드 번호
    val cardUri: String? = null,
    val nuyaType: Int? = -1,
    val memberIcon: Int? = 0,
) {
    companion object {
        val memberIcons = listOf(
            R.drawable.member_icon_1,
            R.drawable.member_icon_2,
            R.drawable.member_icon_3,
            R.drawable.member_icon_4,
            R.drawable.member_icon_5,
            R.drawable.member_icon_6,
            R.drawable.member_icon_7)
        val memberIconsCancel = listOf(
            R.drawable.member_icon_cancel_1,
            R.drawable.member_icon_cancel_2,
            R.drawable.member_icon_cancel_3,
            R.drawable.member_icon_cancel_4,
            R.drawable.member_icon_cancel_5,
            R.drawable.member_icon_cancel_6,
            R.drawable.member_icon_cancel_7)
        val memberIconsFocus = listOf(
            R.drawable.member_icon_focus_1,
            R.drawable.member_icon_focus_2,
            R.drawable.member_icon_focus_3,
            R.drawable.member_icon_focus_4,
            R.drawable.member_icon_focus_5,
            R.drawable.member_icon_focus_6,
            R.drawable.member_icon_focus_7)
        val memberIconsColor = listOf(
            SystemPink,
            SystemOrange,
            SystemYellow,
            SystemGreen,
            SecondaryDarkBlue,
            SystemPurple,
            SecondarySystemBlue
        )
    }

}