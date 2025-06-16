package com.example.waygo.data.remote.model

import com.example.waygo.data.remote.model.Room

data class Hotel(
    val id: String,
    val name: String,
    val address: String,
    val rating: Int,
    val imageUrl: String,
    val rooms: List<Room>? = emptyList()
)