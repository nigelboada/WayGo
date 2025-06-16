package com.example.waygo.data.remote.model

data class Room(
    val id: String,
    val roomType: String,
    val price: Double,
    val images: List<String>
)