package com.soha.politicalpredness.ui.elections

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soha.politicalpredness.data.Repository
import com.soha.politicalpredness.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ElectionsViewModel(context: Context, val repository: Repository) : ViewModel() {

    val elections = MutableLiveData<List<Election>>()
    lateinit var savedElections: LiveData<List<Election>>

    val error = MutableLiveData<String>()

    init {
        getElections()
        getSavedElections()
    }

    private fun getElections() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUpcomingElections()?.let {
                if (it.elections.isNullOrEmpty() && !it.error.isNullOrEmpty())
                    error.postValue(it.error)
                else
                    elections.postValue(it.elections)

            }
        }
    }

    private fun getSavedElections() {
        viewModelScope.launch {
            savedElections = repository.getSavedElections()
        }
    }


}