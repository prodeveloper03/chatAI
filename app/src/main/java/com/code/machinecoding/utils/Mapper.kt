package com.code.machinecoding.utils

import com.code.machinecoding.data.local.MessageEntity
import com.code.machinecoding.domain.model.ChatMessage
import com.code.machinecoding.domain.model.FileMeta
import com.code.machinecoding.domain.model.Thumbnail

fun MessageEntity.toDomain(): ChatMessage {
    val msgType = MessageType.valueOf(type)
    return ChatMessage(
        id = id,
        message = message,
        type = msgType,
        file = if (msgType == MessageType.FILE) {
            FileMeta(
                path = filePath.orEmpty(),
                fileSize = fileSize ?: 0L,
                thumbnail = Thumbnail(thumbnailPath)
            )
        } else null,
        sender = Sender.valueOf(sender),
        timestamp = timestamp
    )
}

