package com.code.machinecoding.utils

import com.code.machinecoding.data.local.MessageEntity
import com.code.machinecoding.domain.model.ChatMessage
import com.code.machinecoding.domain.model.FileMeta
import com.code.machinecoding.domain.model.Thumbnail

fun MessageEntity.toDomain(): ChatMessage =
    ChatMessage(
        id = id,
        message = message,
        type = MessageType.valueOf(type),
        file = filePath?.let {
            FileMeta(
                path = it,
                fileSize = fileSize ?: 0,
                thumbnail = thumbnailPath?.let { Thumbnail(it) }
            )
        },
        sender = Sender.valueOf(sender),
        timestamp = timestamp
    )
