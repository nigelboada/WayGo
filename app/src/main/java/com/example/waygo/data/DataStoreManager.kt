package com.example.waygo.data

import android.content.Context

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore



// Extensión en Context para inicializar DataStore
val Context.dataStore by preferencesDataStore(name = "settings")

class DataStoreManager(private val context: Context) {

    private object PreferencesKeys {
        val USER_LANGUAGE = stringPreferencesKey("user_language")
        val DARK_THEME = booleanPreferencesKey("dark_theme")
    }

    // Flow para el idioma guardado (valor por defecto "es")
    val userLanguageFlow: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }
        .map { preferences ->
            preferences[PreferencesKeys.USER_LANGUAGE] ?: "es"
        }

    // Flow para el tema (valor por defecto: false = tema claro)
    val isDarkThemeFlow: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }
        .map { preferences ->
            preferences[PreferencesKeys.DARK_THEME] == true
        }

    // Función para actualizar el idioma
    suspend fun setUserLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_LANGUAGE] = language
        }
    }

    // Función para actualizar el tema
    suspend fun setDarkTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME] = isDark
        }
    }
}