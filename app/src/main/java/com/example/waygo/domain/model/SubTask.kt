package com.example.waygo.domain.model

data class SubTask(
    val id: Int = 0,
    val parentTaskId: Int,
    val title: String,
    val description: String,
)