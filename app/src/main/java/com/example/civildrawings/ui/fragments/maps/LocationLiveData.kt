package com.example.civildrawings.ui.fragments.maps

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import com.example.civildrawings.dto.LocationDetails
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationLiveData(context: Context) : LiveData<LocationDetails>() {


    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallBack)
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationClient.lastLocation.addOnSuccessListener {
            setLocationData(it)
        }
        startLocationUpdates()
    }


    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.myLooper()!!
        )
    }

    private val locationCallBack = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            locationResult ?: return
            for (location in locationResult.locations) {
                setLocationData(location)
            }

        }

    }

    private fun setLocationData(location: Location?) {
        if (location != null) {
            value = LocationDetails(location.longitude.toString(), location.latitude.toString())
        }
    }

    companion object {
        val ONE_MINUTE: Long = 60000
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = ONE_MINUTE
            fastestInterval = ONE_MINUTE /4
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
    }
}