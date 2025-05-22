package com.example.waygo.domain.model

data class Preferences(
    val id: Int,
    val notificationsEnabled: Boolean,
    val preferredLanguage: String,
    val theme: String
)
