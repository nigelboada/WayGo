package com.example.waygo.domain.repository

import com.example.waygo.domain.model.Image

interface ImageRepository {
    fun uploadPhoto(image: Image): Boolean
    fun getAllImages(): List<Image>
    fun getImageById(id: Int): Image?
    fun updateImage(image: Image): Boolean
    fun deleteImage(id: Int): Boolean
}
