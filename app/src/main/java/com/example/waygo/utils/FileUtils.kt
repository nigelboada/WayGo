package com.example.waygo.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.*

object FileUtils {


    fun saveBitmapToInternal(context: Context, bitmap: Bitmap, tripId: String): String? {
        val dir = File(context.filesDir, "trip_images/$tripId")
        dir.mkdirs()
        val file = File(dir, "photo_${System.currentTimeMillis()}.jpg")
        return try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }
            file.absolutePath
        } catch (e: Exception) {
            null
        }
    }


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
