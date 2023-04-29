package com.s1aks.shiftgen_dispatcher.data

import android.content.SharedPreferences

class LocalSecureStore(private val preferences: SharedPreferences) {
    var accessToken: String?
        get() = preferences.getString("access_token", "")
        set(value) = preferences.edit().putString("access_token", value).apply()

    var refreshToken: String?
        get() = preferences.getString("access_token", "")
        set(value) = preferences.edit().putString("access_token", value).apply()
}