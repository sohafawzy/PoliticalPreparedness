package com.soha.politicalpredness.data.api.responses

import com.soha.politicalpredness.models.Office
import com.soha.politicalpredness.models.Official
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepresentativesResponse(
    val officials: List<Official>?,
    val offices: List<Office>?,
    val error: String?
)