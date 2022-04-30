package com.example.civildrawings.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.civildrawings.R
import com.example.civildrawings.databinding.FragmentMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MapsFragment : Fragment() {
    private lateinit var binding: FragmentMapsBinding
    private lateinit var buttonGetCurrentLocation: FloatingActionButton
    private lateinit var mapsFragmentViewModel: MapsFragmentViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var locationListner: LocationListener
    private val locationPermissionCode = 101
    private val DEFAULT_ZOOM = 20F
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        Log.i(tag, "testing - maps ready")

        val jena = LatLng(33.602361841189, 72.98659947140648)
        mMap.addMarker(MarkerOptions().position(jena).title("Marker in Jena"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jena, DEFAULT_ZOOM))


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())


        binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        mapsFragmentViewModel = ViewModelProvider(this)[MapsFragmentViewModel::class.java]
        var view = binding.root

        return view

    }


    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        locationListner = object : LocationListener {
            override fun onLocationChanged(p0: Location) {
                Log.i(tag, "testing - onLocationChanged")
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        binding.btGetCurrentLocation.setOnClickListener {
            mapsFragmentViewModel.getCurrentLocation()
//            locationManager =
//                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            locationManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER,
//                10,
//                10f,
//                locationListner
//            )
//            locationManager.requestLocationUpdates(
//                LocationManager.NETWORK_PROVIDER,
//                10,
//                10f,
//                locationListner
//            )

            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    locationPermissionCode
                )
            }
            else{
                fusedLocationClient.lastLocation
                    .addOnSuccessListener {
                        if (it == null) {
                            Log.i(tag, "testing - location is null")
                        } else {
                            var loc = LatLng(it.latitude, it.longitude)
                            mMap.addMarker(MarkerOptions().position(loc).title("Current Location"))
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, DEFAULT_ZOOM))
                            Log.i(tag,"testing - longitude: ${it.longitude.toString()} - latitude: ${it.latitude.toString()}")
                        }
                    }
            }
        }

    }




}