package com.example.civildrawings.ui.fragments

import android.util.Log
import androidx.lifecycle.ViewModel

class MapsFragmentViewModel: ViewModel() {
    private  val tag: String = "civildrawings"


    fun getCurrentLocation(){
        Log.i(tag, "testing - getCurrentLocation")
    }


}