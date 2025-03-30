package com.example.waygo.models

data class User(
    val id: String,
    val name: String,
    val email: String,
    val profilePicture: String? = null
)
