package com.soha.politicalpredness.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Representative(
    val office: Office,
    val official: Official?
) : Parcelable