package com.example.civildrawings.ui.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.civildrawings.ui.main.LocationLiveData

class MapsFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private  val tag: String = "civildrawings"

    private val locationLiveData = LocationLiveData(application.applicationContext)
    fun getLocationLiveData() = locationLiveData




}