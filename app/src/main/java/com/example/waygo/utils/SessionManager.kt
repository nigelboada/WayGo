package com.example.waygo.utils

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREF_NAME = "waygo_prefs"
    private const val KEY_LOGGED_IN = "logged_in"

    fun isLoggedIn(context: Context): Boolean {
        val prefs = getPrefs(context)
        return prefs.getBoolean(KEY_LOGGED_IN, false)
    }

    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_LOGGED_IN, isLoggedIn).apply()
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}
