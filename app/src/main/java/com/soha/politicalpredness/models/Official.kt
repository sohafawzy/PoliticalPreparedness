package com.soha.politicalpredness.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Official(
    val name: String,
    val party: String? = null,
    val urls: List<String>? = null,
    val photoUrl: String? = null,
    val channels: List<Channel>? = null
) : Parcelable