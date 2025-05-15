package com.example.waygo.di.dto

data class HotelDto(
    val locationId: String?,
    val name: String?,
    val locationString: String?,
    val rating: String?, // ← l'API retorna un String
    val photo: PhotoDto?,
    val price: String?
)

data class PhotoDto(
    val images: ImagesDto?
)

data class ImagesDto(
    val medium: ImageDto?
)

data class ImageDto(
    val url: String?
)

