package com.example.waygo.data.local.entity


import androidx.room.Entity


@Entity(tableName = "preferences", primaryKeys = ["id"])
data class PreferencesEntity(
    val id: Int,
    val notificationsEnabled: Boolean,
    val preferredLanguage: String,
    val theme: String
)