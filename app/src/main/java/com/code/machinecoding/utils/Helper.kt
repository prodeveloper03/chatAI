package com.code.machinecoding.utils

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