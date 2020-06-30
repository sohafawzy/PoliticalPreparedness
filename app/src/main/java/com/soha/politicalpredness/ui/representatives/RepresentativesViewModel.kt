package com.soha.politicalpredness.ui.representatives

import android.content.Context
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soha.politicalpredness.MyLocationListener
import com.soha.politicalpredness.data.Repository
import com.soha.politicalpredness.models.Address
import com.soha.politicalpredness.models.Representative
import com.soha.politicalpredness.utils.LocationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepresentativesViewModel(val context: Context, val repository: Repository) : ViewModel(),
    MyLocationListener {
    val address = MutableLiveData<Address>(Address("", "", "", "", ""))
    val representatives = MutableLiveData<List<Representative>>()
    val locationUtils = LocationUtils(context)
    val error = MutableLiveData<String>()

    init {
        locationUtils.setLocationListener(this)
    }

    override fun onLocationReady(location: Location) {
        kotlin.runCatching {
            locationUtils.getAddressByLocation(context, location)?.let {
                address.postValue(it)
            }
        }.onFailure {
            error.postValue("Couldn't get location")
        }
    }

    fun onSearchClick(address: Address) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRepresentatives(address.getAddress())?.let {
                if (it.officials == null && it.offices == null && !it.error.isNullOrEmpty()) {
                    error.postValue(it.error)
                } else {
                    val list = arrayListOf<Representative>()
                    it.offices?.map { office ->
                        office.officials.map { pos ->
                            list.add(Representative(office, it.officials?.get(pos)))
                        }
                    }
                    representatives.postValue(list)
                }
            }
        }
    }

}