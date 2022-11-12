package com.youme.naya.database.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "naya_card")
data class Card(

    @PrimaryKey(autoGenerate = true) val NayaCardId: Int,
//    @PrimaryKey val UserId: Int,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "eng_name") val engName: String? = null,
    @ColumnInfo(name = "kind") val kind: Int,
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "mobile") val mobile: String? = null,
    @ColumnInfo(name = "address") val address: String? = null,
    @ColumnInfo(name = "company") val company: String? = null,
    @ColumnInfo(name = "team") val team: String? = null,
    @ColumnInfo(name = "role") val role: String? = null,
    @ColumnInfo(name = "background") val background: String? = null,
    @ColumnInfo(name = "logo") val logo: String? = null,
    @ColumnInfo(name = "fax") val fax: String? = null,
    @ColumnInfo(name = "tel") val tel: String? = null,
    @ColumnInfo(name = "memo1") val memo1: String? = null,
    @ColumnInfo(name = "memo2") val memo2: String? = null,
    @ColumnInfo(name = "memo3") val memo3: String? = null,
    @ColumnInfo(name = "memo_content") val memoContent: String? = null,
    @ColumnInfo(name = "path") val path: String? = null

)
// mainCardId : NayaCardId 1개
// 카드번호 Unique ID
// filename <- String
// ImageURI <- 앱저장소에 들어있는 위치(외부 개별 앱저장소)