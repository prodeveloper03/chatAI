package com.code.machinecoding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.machinecoding.data.repository.ChatRepository
import com.code.machinecoding.domain.model.ChatMessage
import com.code.machinecoding.utils.Response
import com.code.machinecoding.utils.SeedMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repo: ChatRepository
) : ViewModel() {

    private val _messagesState = MutableStateFlow<Response<List<ChatMessage>>>(Response.Loading)
    val messagesState: StateFlow<Response<List<ChatMessage>>> = _messagesState.asStateFlow()

    init {
        viewModelScope.launch {
            repo.insertSeedMessagesIfNeeded(SeedMessages.seed())
            loadMessages()
        }
    }

    fun loadMessages() {
        viewModelScope.launch {
            _messagesState.value = Response.Loading
            _messagesState.value = repo.loadMessages()
        }
    }

    fun sendTextMessage(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            repo.sendTextMessage(text)
            loadMessages()
        }
    }
}

