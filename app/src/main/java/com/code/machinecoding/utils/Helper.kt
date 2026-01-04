package com.code.machinecoding.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun formatFileSize(size: Long?): String {
    if (size == null) return ""
    val kb = size / 1024f
    val mb = kb / 1024f
    return if (mb >= 1f) String.format("%.1f MB", mb) else String.format("%.1f KB", kb)
}



fun copyUriToCache(context: Context, uri: Uri): Result<String> =
    runCatching {
        val input = context.contentResolver.openInputStream(uri)
            ?: error("Unable to open image")

        val file = File(
            context.cacheDir,
            "img_${System.currentTimeMillis()}.jpg"
        )

        file.outputStream().use { output ->
            input.use { it.copyTo(output) }
        }

        file.absolutePath
    }

fun saveBitmapToCache(
    context: Context,
    bitmap: Bitmap
): Result<String> =
    runCatching {
        val file = File(
            context.cacheDir,
            "cam_${System.currentTimeMillis()}.jpg"
        )

        file.outputStream().use {
            if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)) {
                error("Bitmap compression failed")
            }
        }

        file.absolutePath
    }