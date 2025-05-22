package com.example.waygo.domain.model

data class User(
    val id: Int,
    val userId: String,
    val username: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val birthdate: Long,
    val address: String,
    val country: String,
    val acceptEmails: Boolean,
    val profilePictureUrl: String? = null,
    val bio: String = "",
    val followers: Int = 0,
    val following: Int = 0,
    val totalLikes: Int = 0,
    val uploadedRoutes: Int = 0
)
