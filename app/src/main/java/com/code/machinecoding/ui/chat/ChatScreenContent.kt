package com.code.machinecoding.ui.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.code.machinecoding.domain.model.ChatMessage
import com.code.machinecoding.ui.common.ErrorView
import com.code.machinecoding.ui.common.LoadingView
import com.code.machinecoding.utils.Response
import com.code.machinecoding.utils.rememberImeBottom


@Composable
fun ChatScreenContent(
    state: Response<List<ChatMessage>>,
    onSendText: (String) -> Unit,
    onAttachClick: () -> Unit,
    onImageClick: (String) -> Unit
) {
    val listState = rememberLazyListState()

    val messages = (state as? Response.Success)?.data.orEmpty()
    val imeBottom = rememberImeBottom()

    val isNearBottom by remember {
        derivedStateOf {
            val lastVisible =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisible != null && lastVisible >= messages.lastIndex - 1
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        when (state) {
            is Response.Loading -> LoadingView()

            is Response.Error -> ErrorView(state.message)

            is Response.Success -> {

                MessageList(
                    messages = messages,
                    listState = listState,
                    onImageClick = onImageClick,
                    modifier = Modifier.weight(1f)
                )

                MessageInputBar(
                    onSendText = onSendText,
                    onAttachClick = onAttachClick,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .imePadding()
                )
            }
        }
    }

    // When user open the chat keep the latest message visible
    LaunchedEffect(imeBottom) {
        if (
            imeBottom > 0 &&
            messages.isNotEmpty() &&
            isNearBottom
        ) {
            listState.scrollToItem(messages.lastIndex)
        }
    }

    // When new message arrives - auto scroll
    LaunchedEffect(messages.size) {
        if (
            messages.isNotEmpty() &&
            isNearBottom
        ) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }
}





