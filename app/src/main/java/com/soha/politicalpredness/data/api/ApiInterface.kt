package com.soha.politicalpredness.data.api

import com.soha.politicalpredness.data.api.responses.ElectionsResponse
import com.soha.politicalpredness.data.api.responses.RepresentativesResponse
import com.soha.politicalpredness.data.api.responses.VoterInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("elections")
    suspend fun getElections(@Query("key") apiKey: String): Response<ElectionsResponse>

    @GET("voterinfo")
    suspend fun getVoterInfo(
        @Query("key") apiKey: String,
        @Query("address") address: String,
        @Query("electionId") electionId: Int
    ): Response<VoterInfoResponse>

    @GET("representatives")
    suspend fun getRepresentatives(
        @Query("key") apiKey: String,
        @Query("address") address: String
    ): Response<RepresentativesResponse>
}