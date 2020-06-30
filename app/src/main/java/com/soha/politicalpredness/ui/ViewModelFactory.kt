package com.soha.politicalpredness.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soha.politicalpredness.data.Repository
import com.soha.politicalpredness.data.db.CivicInfoDB

class ViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Context::class.java, Repository::class.java).newInstance(
            context,
            Repository(context, CivicInfoDB.getDatabase(context).civicInfoDao())
        )
    }

}