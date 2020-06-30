package com.soha.politicalpredness.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Office(
    @Json(name = "name") val name: String,
    @Json(name = "divisionId") val division: Division,
    @Json(name = "officialIndices") val officials: List<Int>
) : Parcelable