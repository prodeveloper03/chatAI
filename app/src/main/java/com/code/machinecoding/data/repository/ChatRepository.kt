package com.code.machinecoding.data.repository



import com.code.machinecoding.data.local.MessageDao
import com.code.machinecoding.data.local.MessageEntity
import com.code.machinecoding.domain.model.ChatMessage
import com.code.machinecoding.utils.MessageType
import com.code.machinecoding.utils.Response
import com.code.machinecoding.utils.Sender
import com.code.machinecoding.utils.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val messageDao: MessageDao
) {


    suspend fun loadMessages(): Response<List<ChatMessage>> {
        return try {
            val messages = messageDao
                .getAllMessages()
                .map { it.toDomain() }

            Response.Success(messages)
        } catch (e: Exception) {
            Response.Error(
                e.localizedMessage ?: "Unable to load messages"
            )
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
    ): Response<Unit> {
        return try {
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
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error(
                e.localizedMessage ?: "Failed to send text message"
            )
        }
    }

    suspend fun sendFileMessage(
        imagePath: String,
        fileSize: Long,
        thumbnailPath: String?,
        caption: String?,
        sender: Sender = Sender.USER
    ): Response<Unit> {
        return try {
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
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error(
                e.localizedMessage ?: "Failed to send file message"
            )
        }
    }
}
