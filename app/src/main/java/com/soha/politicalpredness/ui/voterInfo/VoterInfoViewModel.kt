package com.soha.politicalpredness.ui.voterInfo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soha.politicalpredness.data.Repository
import com.soha.politicalpredness.data.api.responses.VoterInfoResponse
import com.soha.politicalpredness.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VoterInfoViewModel(context: Context, val repository: Repository) : ViewModel() {
    val voterInfo = MutableLiveData<VoterInfoResponse>()
    val error = MutableLiveData<String>()
    val hasElectionInfo = MutableLiveData<Boolean>()
    val hasBallotLocations = MutableLiveData<Boolean>()
    val hasVotingLocations = MutableLiveData<Boolean>()
    val isFollowed = MutableLiveData<Boolean>()

    fun fetchElectionDetails(election: Election) {
        viewModelScope.launch {
            isFollowed.postValue(repository.isElectionFollowed(election.id))
            getVoterInfo(election)
        }
    }

    private fun getVoterInfo(election: Election) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getVoterInfo(election).let {
                if (it?.state != null && it.state.isNotEmpty()) {
                    hasElectionInfo.postValue(
                        !it.state.get(0).electionAdministrationBody.electionInfoUrl.isNullOrEmpty()
                    )
                    hasBallotLocations.postValue(
                        !it.state.get(0).electionAdministrationBody.ballotInfoUrl.isNullOrEmpty()
                    )
                    hasVotingLocations.postValue(
                        !it.state.get(0).electionAdministrationBody.votingLocationFinderUrl.isNullOrEmpty()
                    )
                    voterInfo.postValue(it)
                } else
                    error.postValue(it?.error)

            }
        }
    }


    fun followElection(election: Election) = viewModelScope.launch {
        isFollowed.postValue(
            if (isFollowed.value == true)
                repository.unFollowElection(election)
            else
                repository.followElection(election)
        )
    }

}