package com.example.waygo.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val username: String, // també servirà per comprovar duplicats
    val birthDate: String,           // format "yyyy-MM-dd"
    val address: String,
    val country: String,
    val phone: String,
    val acceptsEmails: Boolean
)
