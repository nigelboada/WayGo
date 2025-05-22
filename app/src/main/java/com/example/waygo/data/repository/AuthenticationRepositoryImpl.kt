package com.example.waygo.data.repository

import android.util.Log
import com.example.waygo.domain.model.User
import com.example.waygo.domain.repository.AuthenticationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepositoryImpl @Inject constructor() : AuthenticationRepository {

    private val users = mutableMapOf<String, Pair<User, String>>()
    private var loggedInUser: User? = null

    // Variable TAG para usar en los logs
    private val TAG = "AuthenticationRepository"

    override fun login(email: String, password: String): User? {
        Log.d(TAG, "Intentando iniciar sesión con el email: $email")
        val userData = users[email]
        return if (userData != null && userData.second == password) {
            loggedInUser = userData.first
            Log.d(TAG, "Inicio de sesión exitoso para el usuario: $email")
            loggedInUser
        } else {
            Log.d(TAG, "Error en el inicio de sesión: Credenciales incorrectas para $email")
            null
        }
    }

    override fun logout() {
        Log.d(TAG, "Cerrando sesión para el usuario: ${loggedInUser?.email ?: "Ninguno"}")
        loggedInUser = null
    }

    override fun resetPassword(email: String): Boolean {
        Log.d(TAG, "Solicitando restablecimiento de contraseña para el email: $email")
        return if (users.containsKey(email)) {
            Log.d(TAG, "Se ha enviado un correo de recuperación a $email")
            true
        } else {
            Log.d(TAG, "No se encontró el email $email para restablecer la contraseña")
            false
        }
    }

    override fun register(user: User, password: String): Boolean {
        Log.d(TAG, "Intentando registrar usuario con email: ${user.email}")
        return if (users.containsKey(user.email)) {
            Log.d(TAG, "El email ${user.email} ya está registrado")
            false
        } else {
            users[user.email] = Pair(user, password)
            Log.d(TAG, "Usuario registrado exitosamente con el email: ${user.email}")
            true
        }
    }

    override fun getCurrentUser(): User? {
        Log.d(TAG, "Obteniendo usuario actualmente logueado: ${loggedInUser?.email ?: "Ninguno"}")
        return loggedInUser
    }
}
