package com.code.machinecoding.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val message: String,
    val type: String,
    val filePath: String?,
    val fileSize: Long?,
    val thumbnailPath: String?,
    val sender: String,
    val timestamp: Long
)
