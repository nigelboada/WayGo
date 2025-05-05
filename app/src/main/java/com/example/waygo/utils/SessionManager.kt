package com.example.waygo.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SessionManager {
    private const val PREF_NAME = "waygo_pref"

    private const val IS_LOGGED_IN = "is_logged_in"
    private const val USER_ID = "user_id"
    private const val LOGIN_TIME = "login_time"
    private const val LOGOUT_TIME = "logout_time"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setLoggedIn(context: Context, loggedIn: Boolean) {
        getPrefs(context).edit { putBoolean(IS_LOGGED_IN, loggedIn) }
    }

    fun isLoggedIn(context: Context): Boolean {
        return getPrefs(context).getBoolean(IS_LOGGED_IN, false)
    }

    fun saveLoginSession(context: Context, userId: String) {
        getPrefs(context).edit {
            putBoolean(IS_LOGGED_IN, true)
            putString(USER_ID, userId)
            putLong(LOGIN_TIME, System.currentTimeMillis())
        }
    }

    fun saveLogoutTime(context: Context) {
        getPrefs(context).edit {
            putLong(LOGOUT_TIME, System.currentTimeMillis())
        }
    }

    fun getUserId(context: Context): String? {
        return getPrefs(context).getString(USER_ID, null)
    }

    fun getLoginTime(context: Context): Long {
        return getPrefs(context).getLong(LOGIN_TIME, 0L)
    }

    fun getLogoutTime(context: Context): Long {
        return getPrefs(context).getLong(LOGOUT_TIME, 0L)
    }

    fun clearSession(context: Context) {
        getPrefs(context).edit { clear() }
    }
}
