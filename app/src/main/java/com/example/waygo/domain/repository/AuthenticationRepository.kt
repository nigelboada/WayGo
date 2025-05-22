package com.example.waygo.domain.repository

import com.example.waygo.domain.model.User

interface AuthenticationRepository {

    fun login(email: String, password: String): User?
    fun logout()
    fun resetPassword(email: String): Boolean
    fun register(user: User, password: String): Boolean
    fun getCurrentUser(): User?
}