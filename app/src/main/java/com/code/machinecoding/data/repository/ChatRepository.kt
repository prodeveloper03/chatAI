package com.code.machinecoding.data.repository



import com.code.machinecoding.data.local.MessageDao
import com.code.machinecoding.data.local.MessageEntity
import com.code.machinecoding.domain.model.ChatMessage
import com.code.machinecoding.utils.MessageType
import com.code.machinecoding.utils.Response
import com.code.machinecoding.utils.Sender
import com.code.machinecoding.utils.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val messageDao: MessageDao
) {


    fun observeMessages(): Flow<List<ChatMessage>> {
        return messageDao
            .observeAllMessages()
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }


    suspend fun insertSeedMessagesIfNeeded(
        seedMessages: List<MessageEntity>
    ) {
        if (messageDao.getMessageCount() == 0) {
            messageDao.insertMessages(seedMessages)
        }
    }


    suspend fun sendTextMessage(
        text: String,
        sender: Sender = Sender.USER
    ) {
        val entity = MessageEntity(
            id = UUID.randomUUID().toString(),
            message = text,
            type = MessageType.TEXT.name,
            filePath = null,
            fileSize = null,
            thumbnailPath = null,
            sender = sender.name,
            timestamp = System.currentTimeMillis()
        )

        messageDao.insertMessage(entity)
    }

    suspend fun sendFileMessage(
        imagePath: String,
        fileSize: Long,
        thumbnailPath: String?,
        caption: String?,
        sender: Sender = Sender.USER
    ) {
        val entity = MessageEntity(
            id = UUID.randomUUID().toString(),
            message = caption.orEmpty(),
            type = MessageType.FILE.name,
            filePath = imagePath,
            fileSize = fileSize,
            thumbnailPath = thumbnailPath,
            sender = sender.name,
            timestamp = System.currentTimeMillis()
        )

        messageDao.insertMessage(entity)
    }

    suspend fun loadMessagesPaged(offset: Int, limit: Int): List<ChatMessage> {
        return messageDao
            .getMessagesPaged(limit = limit, offset = offset)
            .map { it.toDomain() }
    }
}

