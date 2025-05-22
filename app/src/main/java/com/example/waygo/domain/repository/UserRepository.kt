package com.example.waygo.domain.repository

import com.example.waygo.domain.model.User

interface UserRepository {
    suspend fun getUserById(id: String): User?
    suspend fun getAllUsers(): List<User>
    suspend fun updateUser(user: User): Boolean
    suspend fun deleteUser(id: Int): Boolean
    suspend fun insertUser(user: User): Boolean
    suspend fun getUserByFirebaseUid(firebaseUid: String): User?
}