package com.example.waygo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val name: String,
    val username: String,
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