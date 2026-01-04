package com.code.machinecoding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.machinecoding.data.repository.ChatRepository
import com.code.machinecoding.domain.model.ChatMessage
import com.code.machinecoding.utils.Response
import com.code.machinecoding.utils.SeedMessages
import com.code.machinecoding.utils.Sender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repo: ChatRepository
) : ViewModel() {

    private val _messagesState =
        MutableStateFlow<Response<List<ChatMessage>>>(Response.Loading)

    val messagesState: StateFlow<Response<List<ChatMessage>>> =
        _messagesState.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()

    init {
        observeMessages()
        seedMessagesIfNeeded()
    }

    private fun observeMessages() {
        viewModelScope.launch {
            repo.observeMessages()
                .collect { messages ->
                    _messagesState.value = Response.Success(messages)
                }
        }
    }

    private fun seedMessagesIfNeeded() {
        viewModelScope.launch {
            repo.insertSeedMessagesIfNeeded(
                SeedMessages.seed()
            )
        }
    }

    fun sendTextMessage(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            repo.sendTextMessage(text.trim())
            // Simulating AI typing
            simulateAiTyping()
        }
    }

    fun sendImageMessage(
        imagePath: String,
        fileSize: Long,
        thumbnailPath: String?,
        caption: String?
    ) {
        viewModelScope.launch {
            repo.sendFileMessage(
                imagePath = imagePath,
                fileSize = fileSize,
                thumbnailPath = thumbnailPath,
                caption = caption
            )

            simulateAiTyping()
        }
    }

    private suspend fun simulateAiTyping() {
        _isTyping.value = true

       delay(1500)

        repo.sendTextMessage(
            text = "Thanks for reaching out! 😊",
            sender = Sender.AGENT
        )

        _isTyping.value = false
    }
}



