package com.code.machinecoding.ui.chat

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.code.machinecoding.ui.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    viewModel: ChatViewModel
) {
    val state = viewModel.messagesState.collectAsStateWithLifecycle()

    ChatScreenContent(
        state = state.value,
        onSendText = viewModel::sendTextMessage,
        onAttachClick = { /* image picker later */ }
    )
}