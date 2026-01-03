package com.code.machinecoding.ui.chat

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.code.machinecoding.domain.model.ChatMessage
import com.code.machinecoding.ui.common.ErrorView
import com.code.machinecoding.ui.common.LoadingView
import com.code.machinecoding.utils.Response


@Composable
fun ChatScreenContent(
    state: Response<List<ChatMessage>>,
    onSendText: (String) -> Unit,
    onAttachClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding() // moves input above keyboard
    ) {
        when (state) {
            is Response.Loading -> LoadingView()
            is Response.Error -> ErrorView(state.message)
            is Response.Success -> {
                MessageList(
                    messages = state.data,
                    modifier = Modifier.weight(1f)
                )
                MessageInputBar(
                    onSendText = onSendText,
                    onAttachClick = onAttachClick
                )
            }
        }
    }
}