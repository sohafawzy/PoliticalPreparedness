package com.soha.politicalpredness.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.soha.politicalpredness.R
import com.soha.politicalpredness.data.api.RetrofitConfig
import com.soha.politicalpredness.data.api.responses.ElectionsResponse
import com.soha.politicalpredness.data.api.responses.RepresentativesResponse
import com.soha.politicalpredness.data.api.responses.VoterInfoResponse
import com.soha.politicalpredness.data.db.CivicInfoDao
import com.soha.politicalpredness.models.Election
import com.soha.politicalpredness.utils.ErrorUtils.parseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class Repository(val context: Context, val civicInfoDao: CivicInfoDao) {

    suspend fun getUpcomingElections(): ElectionsResponse? {
        RetrofitConfig().getApiServices()?.let {
            try {
                val response = it.getElections(context.getString(R.string.api_key))
                if (response.isSuccessful) {
                    return response.body()
                } else {
                    return ElectionsResponse(emptyList(), parseError(context, response.errorBody()))
                }
            } catch (e: Exception) {
                return ElectionsResponse(
                    emptyList(),
                    context.getString(R.string.error_no_internet_connection)
                )
            }
        }
        return ElectionsResponse(emptyList(), context.getString(R.string.error_server))
    }

    fun getSavedElections(): LiveData<List<Election>> {
        return civicInfoDao.getSavedElection()
    }


    suspend fun getVoterInfo(election: Election): VoterInfoResponse? {
        RetrofitConfig().getApiServices()?.let {
            try {
                val response = it.getVoterInfo(
                    context.getString(R.string.api_key),
                    election.division.getAddress(),
                    election.id
                )
                return if (response.isSuccessful)
                    response.body()
                else
                    VoterInfoResponse(
                        null, emptyList(),
                        parseError(context, response.errorBody())
                    )
            } catch (e: Exception) {
                return VoterInfoResponse(
                    null, emptyList(),
                    context.getString(R.string.error_no_internet_connection)
                )
            }
        }
        return VoterInfoResponse(
            null, emptyList(),
            context.getString(R.string.error_server)
        )
    }

    suspend fun getRepresentatives(address: String): RepresentativesResponse? {
        RetrofitConfig().getApiServices()?.let {
            try {
                val response = it.getRepresentatives(context.getString(R.string.api_key), address)
                return if (response.isSuccessful)
                    response.body()
                else
                    RepresentativesResponse(
                        null,
                        null,
                        parseError(context, response.errorBody())
                    )
            } catch (e: Exception) {
                return RepresentativesResponse(
                    null,
                    null,
                    context.getString(R.string.error_no_internet_connection)
                )
            }
        }
        return RepresentativesResponse(null, null, context.getString(R.string.error_server))
    }

    suspend fun followElection(election: Election): Boolean {
        return GlobalScope.async(Dispatchers.IO) {
            civicInfoDao.followElection(election)
            return@async true
        }.await()
    }

    suspend fun unFollowElection(election: Election): Boolean {
        return GlobalScope.async(Dispatchers.IO) {
            civicInfoDao.unFollowElection(election.id)
            return@async false
        }.await()
    }

    suspend fun isElectionFollowed(electionId: Int): Boolean {
        return civicInfoDao.getElection(electionId) != null
    }
}