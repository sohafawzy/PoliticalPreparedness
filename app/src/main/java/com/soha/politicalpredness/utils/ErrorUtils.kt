package com.soha.politicalpredness.utils

import android.content.Context
import com.soha.politicalpredness.R
import okhttp3.ResponseBody
import org.json.JSONObject

object ErrorUtils {
    fun parseError(context: Context, response: ResponseBody?): String? {
        try {
            response?.let {
                val jObjError = JSONObject(response.string())
                return jObjError.getJSONObject("error").getString("message")
            }
            return context.getString(R.string.error_server)
        } catch (e: Exception) {
            return context.getString(R.string.error_server)
        }
    }
}