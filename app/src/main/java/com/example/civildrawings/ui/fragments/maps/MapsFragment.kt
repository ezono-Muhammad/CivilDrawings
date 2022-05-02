package com.example.civildrawings.ui.fragments.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import com.example.civildrawings.R
import com.example.civildrawings.databinding.FragmentMapsBinding
import com.example.civildrawings.dto.LocationDetails
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment(), LifecycleObserver {
    private var currentLatLng: LocationDetails? = null
    private lateinit var binding: FragmentMapsBinding
    private lateinit var mapsFragmentViewModel: MapsFragmentViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var locationListner: LocationListener
    private val locationPermissionCode = 101
    private val defaultZoom = 15F


    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        Log.i(tag, "testing - maps ready")
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        val locationButton= (binding.root.findViewById<View>(Integer.parseInt("1")).parent as View).findViewById<View>(Integer.parseInt("2"))
        val rlp=locationButton.layoutParams as (RelativeLayout.LayoutParams)
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP,0)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE)
        rlp.setMargins(0,0,30,30);

        mMap.setOnMapClickListener {
            Log.i(tag,"testing - clicked on map ${it.latitude}, ${it.longitude}")
            Toast.makeText(requireActivity(),"Latitude: ${it.latitude} \n Longitude: ${it.longitude}",Toast.LENGTH_SHORT).show()
        }

        mMap.setOnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
            val markerName = marker
            Log.i(tag, "testing - ${marker.position}")
            Log.i(tag, "testing - ${marker.title}")
            Log.i(tag, "testing - ${marker.id}")
            Log.i(tag, "testing - ${marker.tag}")
            false
        }



        mMap.setOnMapLongClickListener {
            mMap.addMarker(MarkerOptions().position(it).title("Selected Building"))
            Log.i(tag,"testing - long clicked on map latitude: ${it.latitude} , longitude: ${it.longitude}")
        }
        if (currentLatLng != null) {
            goTOCurrentLocation(5F)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        mapsFragmentViewModel = ViewModelProvider(this)[MapsFragmentViewModel::class.java]
        mapsFragmentViewModel.getLocationLiveData().observe(viewLifecycleOwner) {
            currentLatLng = it
        }
        return binding.root
    }


    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.btGetCurrentLocation.setOnClickListener {
            goTOCurrentLocation(18F)

        }


    }

    private fun goTOCurrentLocation(zoom: Float = 0F) {
        prepRequestLocation()
        val loc = LatLng(currentLatLng!!.latitude.toDouble(), currentLatLng!!.longitude.toDouble())
        mMap.addMarker(MarkerOptions().position(loc).title("Current Location"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom))
    }

    private fun prepRequestLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
            return
        }
    }

    private fun requestLocationUpdates() {
        mapsFragmentViewModel.getLocationLiveData().observe(requireActivity()) {
            currentLatLng = it
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            locationPermissionCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocationUpdates()
                } else {
                    Toast.makeText(
                        context,
                        "Failed to get location permissions",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}


