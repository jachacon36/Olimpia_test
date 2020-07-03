package com.example.olimpia_test

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import pub.devrel.easypermissions.EasyPermissions


class LocationActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnMyLocationButtonClickListener,
    EasyPermissions.PermissionCallbacks {

    private lateinit var mMap: GoogleMap
    private val REQUEST_LOCATION_PERMISSION : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        requestLocationPermission()
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        mMap.isMyLocationEnabled = true
        onMylocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @SuppressLint("MissingPermission")
    fun requestLocationPermission() {
        val perms =
            arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            mMap.isMyLocationEnabled = true
            onMylocation()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Please grant the location permission",
                REQUEST_LOCATION_PERMISSION,
                *perms
            )
        }
    }

    override fun onMyLocationClick(p0: Location) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(p0.latitude,p0.longitude), 16F))
    }

    override fun onMyLocationButtonClick(): Boolean {
        requestLocationPermission()
        return false
    }

    private fun onMylocation(){
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val locationRequest = LocationRequest()
        val locationSettingsRequest = LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()
        val client = LocationServices.getSettingsClient(this).checkLocationSettings(locationSettingsRequest)
        client.addOnSuccessListener {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(locationResult.lastLocation.latitude,locationResult.lastLocation.longitude), 16F))

                }
            }, Looper.myLooper())
        }.addOnFailureListener { exception ->
        }
    }

    fun startNextActivity(view: View) {
        startActivity(Intent(this, NetworkActivity::class.java))
        finishAffinity()
    }

}
