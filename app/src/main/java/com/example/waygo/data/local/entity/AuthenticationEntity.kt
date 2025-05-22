package com.example.waygo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authentication")
data class AuthenticationEntity (
    @PrimaryKey
    val email: String,
    val password: String
)