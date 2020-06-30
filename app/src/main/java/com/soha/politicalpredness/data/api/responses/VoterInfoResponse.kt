package com.soha.politicalpredness.data.api.responses

import com.soha.politicalpredness.models.Election
import com.soha.politicalpredness.models.State
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VoterInfoResponse(
    val election: Election?,
    val state: List<State>,
    var error: String?
)