package com.example.waygo.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun setUsername(username: String) {
        prefs.edit { putString("username", username) }
    }

    fun getUsername(): String? = prefs.getString("username", null)

    fun setDarkModeEnabled(enabled: Boolean) {
        prefs.edit { putBoolean("dark_mode", enabled) }
    }

    fun isDarkModeEnabled(): Boolean = prefs.getBoolean("dark_mode", false)

    fun clear() {
        prefs.edit { clear() }
    }
    fun setPassword(password: String) {
        prefs.edit().putString("password", password).apply()
    }

    fun getPassword(): String? = prefs.getString("password", null)

    fun setLanguage(language: String) {
        prefs.edit().putString("language", language).apply()
    }

    fun getLanguage(): String {
        return prefs.getString("language", "Català") ?: "Català"
    }


}
