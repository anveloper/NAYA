package com.youme.naya.database.repository

import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject

// constant 로 이동해야함
const val PREF_NAME = "SETTING"
const val IS_FIRST_KEY = "com.youme.naya.SHARE_PREF_NAYA_IS_FIRST"
class SharedPrefDataSource @Inject constructor(context: Context) {
    private val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getIsFirst(): Boolean {
        return when(sharedPref.getBoolean(IS_FIRST_KEY, true)){
            true -> true
            false -> false
        }
    }

    fun setIsFirst(){
        sharedPref.edit{
            remove(IS_FIRST_KEY)
            putBoolean(IS_FIRST_KEY, false)
        }
    }
    fun resetIsFirst(){
        sharedPref.edit{
            remove(IS_FIRST_KEY)
            putBoolean(IS_FIRST_KEY, true)
        }
    }
}