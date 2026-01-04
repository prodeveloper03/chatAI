package com.code.machinecoding.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diffMillis = now - timestamp
    val minute = 60_000L
    val hour = 60 * minute

    return when {
        diffMillis < minute -> {
            "Just now"
        }

        diffMillis < hour -> {
            val minutes = diffMillis / minute
            "$minutes minute${if (minutes == 1L) "" else "s"} ago"
        }

        isToday(timestamp) -> {
            "Today at ${formatTime(timestamp)}"
        }

        isYesterday(timestamp) -> {
            "Yesterday at ${formatTime(timestamp)}"
        }

        else -> {
            formatFullDate(timestamp)
        }
    }
}

private fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

private fun formatFullDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a",Locale.getDefault())
    return sdf.format(Date(timestamp))
}

private fun isToday(timestamp: Long): Boolean {
    val today = Calendar.getInstance()
    val date = Calendar.getInstance().apply { timeInMillis = timestamp }
    return today.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
            today.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)
}

private fun isYesterday(timestamp: Long): Boolean {
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
    val date = Calendar.getInstance().apply { timeInMillis = timestamp }
    return yesterday.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
            yesterday.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)
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