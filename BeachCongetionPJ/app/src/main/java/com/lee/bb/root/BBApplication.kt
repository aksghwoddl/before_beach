package com.lee.bb.root

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BBApplication: Application() {
    companion object{
        private lateinit var instance : BBApplication
        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = BBApplication()
    }
}