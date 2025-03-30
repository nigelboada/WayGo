package com.example.waygo.services

import com.example.waygo.models.User

class UserService {

    private val users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }

    fun getUserById(id: String): User? {
        return users.find { it.id == id }
    }
}
