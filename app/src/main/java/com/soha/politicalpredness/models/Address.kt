package com.soha.politicalpredness.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    val line1: String?,
    val line2: String? = null,
    val city: String,
    val state: String,
    val zip: String
) : Parcelable {

    fun getAddress(): String {
        var address = ""
        if (!line1.isNullOrEmpty()) address = address.plus("$line1\n")
        if (!line2.isNullOrEmpty()) address = address.plus("$line2\n")
        address = address.plus("$city, $state, $zip")
        return address
    }
}