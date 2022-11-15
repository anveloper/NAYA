package com.youme.naya

import android.app.Application
import com.youme.naya.utils.AppModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CardApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppModule.provide(this)
    }
}