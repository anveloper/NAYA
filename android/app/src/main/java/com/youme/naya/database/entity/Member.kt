package com.youme.naya.database.entity

 import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member")
data class Member (
    @PrimaryKey(autoGenerate = true) var memberId: Int? = null,
    val name : String? = null,
    // 어디서 가져온 정보인지
    val type : String? = null,
    val phoneNum : String? = null,
    val email : String? = null,
    // 기타 기록 사항
    val etcInfo : String? = null,
    // Nuya 카드이면 너야 카드 번호
    val cardId : Int? = null
)