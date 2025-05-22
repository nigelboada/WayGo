package com.example.waygo.data.repository

import android.util.Log
import com.example.waygo.domain.model.Image
import com.example.waygo.domain.repository.ImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepositoryImpl @Inject constructor() : ImageRepository {

    private val images = mutableListOf<Image>()
    private val TAG = "ImageRepository"  // Variable TAG para los logs

    override fun uploadPhoto(image: Image): Boolean {
        Log.d(TAG, "Intentando subir foto con ID: ${image.id}")
        return if (images.any { it.id == image.id }) {
            Log.d(TAG, "La foto con ID: ${image.id} ya existe.")
            false
        } else {
            images.add(image)
            Log.d(TAG, "Foto con ID: ${image.id} subida exitosamente.")
            true
        }
    }

    override fun getAllImages(): List<Image> {
        Log.d(TAG, "Obteniendo todas las imágenes, total: ${images.size}")
        return images.sortedByDescending { it.timestamp }
    }

    override fun getImageById(id: Int): Image? {
        Log.d(TAG, "Buscando imagen con ID: $id")
        val image = images.find { it.id == id }
        if (image != null) {
            Log.d(TAG, "Imagen con ID: $id encontrada.")
        } else {
            Log.d(TAG, "Imagen con ID: $id no encontrada.")
        }
        return image
    }

    override fun updateImage(image: Image): Boolean {
        Log.d(TAG, "Intentando actualizar imagen con ID: ${image.id}")
        val index = images.indexOfFirst { it.id == image.id }
        return if (index != -1) {
            images[index] = image
            Log.d(TAG, "Imagen con ID: ${image.id} actualizada exitosamente.")
            true
        } else {
            Log.d(TAG, "Imagen con ID: ${image.id} no encontrada para actualizar.")
            false
        }
    }

    override fun deleteImage(id: Int): Boolean {
        Log.d(TAG, "Intentando eliminar imagen con ID: $id")
        val result = images.removeIf { it.id == id }
        if (result) {
            Log.d(TAG, "Imagen con ID: $id eliminada exitosamente.")
        } else {
            Log.d(TAG, "Imagen con ID: $id no encontrada para eliminar.")
        }
        return result
    }
}
