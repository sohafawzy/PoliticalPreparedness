package com.soha.politicalpredness.ui.representatives

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soha.politicalpredness.models.Representative
import com.soha.politicalpredness.utils.Utils.openUrl
import java.util.*

class RepresentativeViewModel(val context: Context) : ViewModel() {

    val representative = MutableLiveData<Representative>()
    val hasWeb = MutableLiveData<Boolean>(false)
    val hasFacebook = MutableLiveData<Boolean>(false)
    val hasTwitter = MutableLiveData<Boolean>(false)

    fun bind(rep: Representative) {
        representative.postValue(rep)

    }

    fun onWebClick() {
        representative.value?.let { rep ->
            rep.official?.urls?.let { urls ->
                if (urls.isNotEmpty() && urls.get(0).isNotEmpty()) {
                    openUrl(context, urls.get(0))
                }
            }
        }
    }

    fun onFacebookClick() {
        representative.value?.let { rep ->
            rep.official?.channels?.let {
                it.map { channel ->
                    if (channel.type.toLowerCase(Locale.ENGLISH) == "facebook")
                        openUrl(context, "https://facebook.com/${channel.id}")
                }
            }
        }
    }


    fun onTwitterClick() {
        representative.value?.let { rep ->
            rep.official?.channels?.let {
                it.map { channel ->
                    if (channel.type.toLowerCase(Locale.ENGLISH) == "twitter")
                        openUrl(context, "https://twitter.com/${channel.id}")
                }
            }
        }
    }
}