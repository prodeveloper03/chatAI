package com.code.machinecoding.domain.model

import com.code.machinecoding.utils.MessageType
import com.code.machinecoding.utils.Sender

data class ChatMessage(
    val id: String,
    val message: String,
    val type: MessageType,
    val file: FileMeta?,
    val sender: Sender,
    val timestamp: Long
)
