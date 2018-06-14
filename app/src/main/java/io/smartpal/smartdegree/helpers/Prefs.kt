package io.smartpal.smartdegree.helpers

import android.content.Context
import android.content.SharedPreferences
import io.smartpal.smartdegree.BuildConfig

class Prefs (context: Context) {

    private val prefsFilename = "com.smartpal.application.prefs"
    private val endpointTag = "endpoint"

    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)

    var endpoint: String
        get() = prefs.getString(endpointTag,BuildConfig.ETH_NETWORK_URL)
        set(value) = prefs.edit().putString(endpointTag, value).apply()
}