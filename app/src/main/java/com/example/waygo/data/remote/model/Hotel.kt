package com.example.waygo.data.remote.model


data class Hotel(
    val id: String,
    val name: String,
    val address: String,
    val rating: Int,
    val rooms: List<Room>,
    val imageUrl: String
)
