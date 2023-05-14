package com.s1aks.shiftgen_dispatcher.data

import android.content.SharedPreferences

class LocalSecureStore(private val preferences: SharedPreferences) {
    var login: String?
        get() = preferences.getString("login", "")
        set(value) = preferences.edit().putString("login", value).apply()

    var structureId: Int?
        get() = preferences.getInt("structure_id", -1)
        set(value) = preferences.edit().putInt("login", value ?: -1).apply()

    var accessToken: String?
        get() = preferences.getString("access_token", "")
        set(value) = preferences.edit().putString("access_token", value).apply()

    var refreshToken: String?
        get() = preferences.getString("refresh_token", "")
        set(value) = preferences.edit().putString("refresh_token", value).apply()

    fun clear() {
        accessToken = ""
        refreshToken = ""
    }
}