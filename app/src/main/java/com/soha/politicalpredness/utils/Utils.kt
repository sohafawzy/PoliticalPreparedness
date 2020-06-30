package com.soha.politicalpredness.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object Utils {

    fun openUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }


}