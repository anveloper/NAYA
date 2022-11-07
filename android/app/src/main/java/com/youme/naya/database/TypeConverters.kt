package com.youme.naya.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.youme.naya.database.entity.Member

// List<String>
@ProvidedTypeConverter
class StringListTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun listToJson(value: List<String>): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String> {
        return gson.fromJson(value, Array<String>::class.java).toList()
    }
}

//Member
@ProvidedTypeConverter
class MemberTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun listToJson(value: Member): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): Member {
        return gson.fromJson(value, Member::class.java)
    }
}

// List<Member>
@ProvidedTypeConverter
class MemberListTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun listToJson(value: List<Member>): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Member> {
        return gson.fromJson(value, Array<Member>::class.java).toList()

    }
}
