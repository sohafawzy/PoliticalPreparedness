package com.soha.politicalpredness.utils

import android.Manifest

object Constants {
    const val Base_URL = "https://www.googleapis.com/civicinfo/v2/"
    const val REQUEST_LOCATION_PERMISSIONS = 100
    val PERMISSIONS_LOCATION = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

}