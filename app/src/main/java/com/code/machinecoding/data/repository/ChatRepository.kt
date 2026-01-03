package com.code.machinecoding.data.repository



import com.code.machinecoding.data.local.MessageDao
import com.code.machinecoding.data.local.MessageEntity
import com.code.machinecoding.domain.model.ChatMessage
import com.code.machinecoding.utils.Response
import com.code.machinecoding.utils.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val messageDao: MessageDao
) {

    /**
     * Load all chat messages
     */
    suspend fun loadMessages(): Response<List<ChatMessage>> {
        return withContext(Dispatchers.IO) {
            try {
                val messages = messageDao
                    .getAllMessages()
                    .map { it.toDomain() }

                Response.Success(messages)
            } catch (e: Exception) {
                Response.Error(
                    message = e.localizedMessage ?: "Failed to load messages"
                )
            }
        }
    }


    /**
     * Insert seed data if DB is empty
     */
    suspend fun insertSeedMessagesIfNeeded(
        seedMessages: List<MessageEntity>
    ): Response<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                if (messageDao.getMessageCount() == 0) {
                    messageDao.insertMessages(seedMessages)
                }
                Response.Success(Unit)
            } catch (e: Exception) {
                Response.Error(
                    message =  e.localizedMessage ?: "Failed to insert seed data"
                )
            }
        }
    }

    /**
     * Send text message
     */
    suspend fun sendTextMessage(
        text: String,
        sender: String = "USER"
    ): Response<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                messageDao.insertMessage(
                    MessageEntity(
                        id = UUID.randomUUID().toString(),
                        message = text,
                        type = "TEXT",
                        filePath = null,
                        fileSize = null,
                        thumbnailPath = null,
                        sender = sender,
                        timestamp = System.currentTimeMillis()
                    )
                )
                Response.Success(Unit)
            } catch (e: Exception) {
                Response.Error(
                    message = e.localizedMessage ?: "Failed to send message"
                )
            }
        }
    }

    /**
     * Send file (image) message
     */
    suspend fun sendFileMessage(
        imagePath: String,
        thumbnailPath: String?,
        fileSize: Long,
        caption: String?,
        sender: String = "USER"
    ): Response<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                messageDao.insertMessage(
                    MessageEntity(
                        id = UUID.randomUUID().toString(),
                        message = caption.orEmpty(),
                        type = "FILE",
                        filePath = imagePath,
                        fileSize = fileSize,
                        thumbnailPath = thumbnailPath,
                        sender = sender,
                        timestamp = System.currentTimeMillis()
                    )
                )
                Response.Success(Unit)
            } catch (e: Exception) {
                Response.Error(
                    message =  e.localizedMessage ?: "Failed to send file message"
                )
            }
        }
    }
}