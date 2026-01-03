package com.code.machinecoding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.machinecoding.data.repository.ChatRepository
import com.code.machinecoding.domain.model.ChatMessage
import com.code.machinecoding.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _messagesState = MutableStateFlow<Response<List<ChatMessage>>>(Response.Loading)

    val messagesState: StateFlow<Response<List<ChatMessage>>> = _messagesState.asStateFlow()

    init {
        loadMessages()
    }

    fun loadMessages() {
        viewModelScope.launch {
            _messagesState.value = Response.Loading

            val response = chatRepository.loadMessages()
            _messagesState.value = response
        }
    }

    fun sendTextMessage(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            chatRepository.sendTextMessage(text)
            loadMessages()
        }
    }

    fun sendImageMessage(
        imagePath: String,
        fileSize: Long,
        thumbnailPath: String? = null,
        caption: String? = null
    ) {
        viewModelScope.launch {
            chatRepository.sendFileMessage(
                imagePath = imagePath,
                fileSize = fileSize,
                thumbnailPath = thumbnailPath,
                caption = caption
            )
            loadMessages()
        }
    }
}
