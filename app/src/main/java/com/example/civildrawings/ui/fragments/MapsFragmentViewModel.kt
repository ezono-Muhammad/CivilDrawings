package com.example.civildrawings.ui.fragments

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val DEFAULT_ZOOM = 20F
    private val locationPermissionCode = 111

    private  val tag: String = "civildrawings"



    fun getCurrentLocation(mMap: GoogleMap){
        Log.i(tag, "testing - current location from view model")

    }


}