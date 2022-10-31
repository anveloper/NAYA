package com.youme.naya.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "naya_card")
data class Card(

    @PrimaryKey val NayaCardId: Int,
//    @PrimaryKey val UserId: Int,
    @ColumnInfo(name = "name") val name: String,
//    @ColumnInfo(name = "eng_name") val engName: String,
//    @ColumnInfo(name = "kind") val kind: Int,
//    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "mobile") val mobile: String,
//    @ColumnInfo(name = "address") val address: String,
//    @ColumnInfo(name = "company") val company: String,
//    @ColumnInfo(name = "team") val team: String,
//    @ColumnInfo(name = "role") val role: String,
//    @ColumnInfo(name = "background") val background: String,
//    @ColumnInfo(name = "logo") val logo: String,
//    @ColumnInfo(name = "fax") val fax: String,
//    @ColumnInfo(name = "tel") val tel: String,
//    @ColumnInfo(name = "memo1") val memo1: String,
//    @ColumnInfo(name = "memo2") val memo2: String,
//    @ColumnInfo(name = "memo3") val memo3: String,
//    @ColumnInfo(name = "memo_content") val memo_content: String

)
