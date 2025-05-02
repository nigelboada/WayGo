package com.example.waygo.repository

import android.content.Context
import com.example.waygo.local.AppDatabase
import com.example.waygo.local.entity.UserEntity

class UserRepository(context: Context) {
    private val userDao = AppDatabase.getDatabase(context).userDao()

    suspend fun insertUser(user: UserEntity): Boolean {
        return if (!userDao.isUsernameTaken(user.username)) {
            userDao.insertUser(user)
            true
        } else {
            false
        }
    }

    suspend fun isUsernameTaken(username: String): Boolean {
        return userDao.isUsernameTaken(username)
    }

    suspend fun getUser(username: String): UserEntity? {
        return userDao.getUserByUsername(username)
    }
}
