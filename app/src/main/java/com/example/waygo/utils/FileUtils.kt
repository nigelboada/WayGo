package com.example.waygo.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.*

object FileUtils {
    /**
     * Copia el contingut de [uri] a un fitxer intern dins <filesDir>/trip_images/
     * i retorna la ruta absoluta.
     */
    fun copyUriToInternal(context: Context, uri: Uri, tripId: String): String? {
        val inStream = context.contentResolver.openInputStream(uri) ?: return null
        val imagesDir = File(context.filesDir, "trip_images").apply { mkdirs() }
        val dest = File(imagesDir, "trip_${tripId}_${UUID.randomUUID()}.jpg")
        inStream.use { input ->
            FileOutputStream(dest).use { output ->
                input.copyTo(output)
            }
        }
        return dest.absolutePath
    }
}
