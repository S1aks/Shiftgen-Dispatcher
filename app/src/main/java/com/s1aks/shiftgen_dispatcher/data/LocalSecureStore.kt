package com.s1aks.shiftgen_dispatcher.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class LocalSecureStore(
    context: Context
) {
    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private val preferences = EncryptedSharedPreferences.create(
        context,
        "auth_tokens_secured",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var login: String?
        get() = preferences.getString("login", "")
        set(value) = preferences.edit().putString("login", value).apply()

    var structureId: Int?
        get() = preferences.getInt("structure_id", -1)
        set(value) = preferences.edit().putInt("structure_id", value ?: -1).apply()

    var accessToken: String?
        get() = preferences.getString("access_token", "")
        set(value) = preferences.edit().putString("access_token", value).apply()

    var refreshToken: String?
        get() = preferences.getString("refresh_token", "")
        set(value) = preferences.edit().putString("refresh_token", value).apply()

    fun clear() {
        structureId = -1
        accessToken = ""
        refreshToken = ""
    }
}