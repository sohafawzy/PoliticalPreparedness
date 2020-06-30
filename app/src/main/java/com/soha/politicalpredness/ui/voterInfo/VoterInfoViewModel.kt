package com.soha.politicalpredness.ui.voterInfo

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soha.politicalpredness.data.Repository
import com.soha.politicalpredness.data.api.responses.VoterInfoResponse
import com.soha.politicalpredness.models.Election
import com.soha.politicalpredness.utils.Utils.openUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VoterInfoViewModel(val context: Context, val repository: Repository) : ViewModel() {
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

    fun getVoterInfo(election: Election) {
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

    fun onElectionsInfoClick(view: View) {
        hasElectionInfo.value?.let { hasElectionInfo ->
            voterInfo.value?.state?.get(0)?.electionAdministrationBody?.electionInfoUrl?.let {
                if (hasElectionInfo) openUrl(context, it)
            }
        }
    }

    fun onVotingLocationsClick(view: View) {
        hasVotingLocations.value?.let { hasVotingLocations ->
            voterInfo.value?.state?.get(0)?.electionAdministrationBody?.votingLocationFinderUrl?.let {
                if (hasVotingLocations) openUrl(context, it)
            }
        }
    }

    fun onBallotLocationsClick(view: View) {
        hasBallotLocations.value?.let { hasBallotLocations ->
            voterInfo.value?.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl?.let {
                if (hasBallotLocations) openUrl(context, it)
            }
        }
    }

    fun onFollowClick(view: View, election: Election) = viewModelScope.launch {
        isFollowed.postValue(
            if (isFollowed.value == true)
                repository.unFollowElection(election)
            else
                repository.followElection(election)
        )
    }

}