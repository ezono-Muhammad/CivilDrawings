package com.example.civildrawings.ui.fragments.maps

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MapsFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private  val tag: String = "civildrawings"

    private val locationLiveData = LocationLiveData(application.applicationContext)
    fun getLocationLiveData() = locationLiveData




}