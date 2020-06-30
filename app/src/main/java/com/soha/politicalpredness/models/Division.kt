package com.soha.politicalpredness.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Division(
    val id: String,
    val country: String,
    val state: String
) : Parcelable {

    fun getAddress(): String {
        var address = ""
        if (state.isNotEmpty()) address = address.plus(state)
        if (address.isNotEmpty() && country.isNotEmpty()) address = address.plus(", $country")
        else if (country.isNotEmpty()) address = address.plus(country)
        return address
    }

}