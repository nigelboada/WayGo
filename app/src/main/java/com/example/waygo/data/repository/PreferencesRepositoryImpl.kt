package com.example.waygo.data.repository


import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton
import com.example.waygo.domain.model.Preferences
import com.example.waygo.domain.repository.PreferencesRepository

@Singleton
class PreferencesRepositoryImpl @Inject constructor() : PreferencesRepository {

    private val preferencesList = mutableListOf<Preferences>()
    private val TAG = "PreferencesRepositoryImpl" // Definir un TAG para los logs

    override fun addPreferences(preferences: Preferences): Boolean {
        Log.d(TAG, "Intentando agregar las preferencias con ID: ${preferences.id}")

        return if (preferencesList.any { it.id == preferences.id }) {
            Log.d(TAG, "Las preferencias con ID: ${preferences.id} ya existen")
            false
        } else {
            preferencesList.add(preferences)
            Log.d(TAG, "Las preferencias con ID: ${preferences.id} se han agregado correctamente")
            true
        }
    }

    override fun getPreferences(id: Int): Preferences? {
        Log.d(TAG, "Obteniendo las preferencias con ID: $id")
        return preferencesList.find { it.id == id }
    }

    override fun updatePreferences(
        id: Int,
        notificationsEnabled: Boolean?,
        preferredLanguage: String?,
        theme: String?
    ): Boolean {
        Log.d(TAG, "Intentando actualizar las preferencias con ID: $id")

        val index = preferencesList.indexOfFirst { it.id == id }

        return if (index != -1) {
            val currentPreferences = preferencesList[index]
            preferencesList[index] = currentPreferences.copy(
                notificationsEnabled = notificationsEnabled ?: currentPreferences.notificationsEnabled,
                preferredLanguage = preferredLanguage ?: currentPreferences.preferredLanguage,
                theme = theme ?: currentPreferences.theme
            )
            Log.d(TAG, "Las preferencias con ID: $id se han actualizado correctamente")
            true
        } else {
            Log.d(TAG, "Las preferencias con ID: $id no se encontraron")
            false
        }
    }

    override fun deletePreferences(id: Int): Boolean {
        Log.d(TAG, "Intentando eliminar las preferencias con ID: $id")
        val result = preferencesList.removeIf { it.id == id }
        if (result) {
            Log.d(TAG, "Las preferencias con ID: $id se han eliminado correctamente")
        } else {
            Log.d(TAG, "Las preferencias con ID: $id no se encontraron para eliminar")
        }
        return result
    }
}
