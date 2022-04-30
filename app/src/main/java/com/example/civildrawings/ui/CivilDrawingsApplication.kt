package com.example.civildrawings.ui

import android.app.Application
import android.content.Context

class CivilDrawingsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    companion object {
        lateinit var mInstance: CivilDrawingsApplication
        fun getContext(): Context? {
            return mInstance.applicationContext
        }
    }
}