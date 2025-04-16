package com.example.waygo.models

import java.util.UUID

data class Trip(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val location: String,
    val startDate: String,
    val endDate: String,


)
