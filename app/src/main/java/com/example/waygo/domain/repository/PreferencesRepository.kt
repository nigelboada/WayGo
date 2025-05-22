package com.example.waygo.domain.repository

import com.example.waygo.domain.model.Preferences

interface PreferencesRepository {
    fun addPreferences(preferences: Preferences): Boolean
    fun getPreferences(id: Int): Preferences?
    fun updatePreferences(id: Int, notificationsEnabled: Boolean?, preferredLanguage: String?, theme: String?): Boolean
    fun deletePreferences(id: Int): Boolean
}