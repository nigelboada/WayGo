package com.example.waygo.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.waygo.data.SharedPrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager
) : ViewModel() {

    var language by mutableStateOf(sharedPrefsManager.userLanguage ?: "es")
        private set

    var isDarkTheme by mutableStateOf(sharedPrefsManager.darkTheme)
        private set

    // Actualización del idioma
    fun updateLanguage(newLanguage: String) {
        Log.d("SettingsViewModel", "Actualizando idioma a: $newLanguage")
        sharedPrefsManager.userLanguage = newLanguage
        language = newLanguage
        Log.d("SettingsViewModel", "Idioma actualizado a: $language")
    }

    // Actualización del tema oscuro
    fun updateDarkTheme(isDark: Boolean) {
        Log.d("SettingsViewModel", "Actualizando tema oscuro a: $isDark")
        sharedPrefsManager.darkTheme = isDark
        isDarkTheme = isDark
        Log.d("SettingsViewModel", "Tema oscuro actualizado a: $isDarkTheme")
    }
}
