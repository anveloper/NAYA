package com.youme.naya.database.entity

 import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member")
data class Member (
    @PrimaryKey(autoGenerate = true) var memberId: Int? = null,
    val name : String? = null,
    val type : String? = null,
    val phoneNum : String? = null,
    val email : String? = null,
    val etcInfo : String? = null,
    val cardId : Int? = null
)