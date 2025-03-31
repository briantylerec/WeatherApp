package com.trackforce.weatherapp.preferences

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import javax.inject.Inject

class SecurePreferences @Inject constructor(private val context: Context) {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun <T> saveObject(key: String, obj: T) {
        val json = Gson().toJson(obj)
        sharedPreferences.edit().putString(key, json).apply()
    }

    fun <T> getObject(key: String, classOfT: Class<T>): T? {
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            Gson().fromJson(json, classOfT)
        } else {
            null
        }
    }
}
