package com.example.waygo.utils

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREF_NAME = "waygo_pref"  // Nom del fitxer de les preferències
    private const val IS_LOGGED_IN = "is_logged_in"  // Clau per saber si l'usuari està logat

    // Funció per establir l'estat de logat a les preferències
    fun setLoggedIn(context: Context, loggedIn: Boolean) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(IS_LOGGED_IN, loggedIn)  // Guardem l'estat de connexió
        editor.apply()  // Aplica els canvis
    }

    // Funció per comprovar si l'usuari està logat o no
    fun isLoggedIn(context: Context): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)  // Retorna "false" si no hi ha cap estat emmagatzemat
    }
}
