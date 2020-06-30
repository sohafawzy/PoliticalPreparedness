package com.soha.politicalpredness.data.api.responses

import com.soha.politicalpredness.models.Election
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ElectionsResponse(
    val elections: List<Election>,
    var error: String? = ""
)