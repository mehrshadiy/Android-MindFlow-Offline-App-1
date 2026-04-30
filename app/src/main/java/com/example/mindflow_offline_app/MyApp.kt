package com.example.mindflow_offline_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Singleton

@HiltAndroidApp
class MyApp: Application(){
    override fun onCreate() {
        super.onCreate()
    }
}