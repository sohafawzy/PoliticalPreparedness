package com.soha.politicalpredness.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.soha.politicalpredness.MyLocationListener
import com.soha.politicalpredness.R
import com.soha.politicalpredness.models.Address

class LocationUtils(private val context: Context) {

    private lateinit var locationListener: MyLocationListener

    fun setLocationListener(locationListener: MyLocationListener) {
        this.locationListener = locationListener
    }

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage(R.string.message_open_gps)
            .setCancelable(false)
            .setPositiveButton(R.string.label_yes) { dialog, click ->
                context.startActivity(
                    Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    )
                )
            }
            .setNegativeButton(R.string.label_no) { dialog, click -> dialog.cancel() }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    fun areLocationPermissionsGranted(context: Context): Boolean {
        Constants.PERMISSIONS_LOCATION.map {
            return ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
        return true
    }


    fun getAddressByLocation(context: Context, location: Location): Address? {
        val addresses = Geocoder(context).getFromLocation(location.latitude, location.longitude, 1)
        addresses?.let {
            addresses.get(0)?.let { address ->
                return Address(
                    address.thoroughfare,
                    address.subThoroughfare,
                    address.locality,
                    address.adminArea,
                    address.postalCode
                )
            }
        }
        return null
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(context: Context) {
        if (!isLocationEnabled())
            buildAlertMessageNoGps()
        else
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                it.result?.let { location ->
                    locationListener.onLocationReady(location)
                    return@let
                }
                requestLocation()
            }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val locationRequest = LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(0).setFastestInterval(0).setNumUpdates(1)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    locationResult?.let {
                        it.lastLocation?.let {
                            locationListener.onLocationReady(it)
                        }
                    }
                }
            },
            Looper.getMainLooper()
        )
    }
}