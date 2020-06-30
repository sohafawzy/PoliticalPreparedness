package com.soha.politicalpredness

import android.location.Location

interface MyLocationListener {
    fun onLocationReady(location: Location)
}