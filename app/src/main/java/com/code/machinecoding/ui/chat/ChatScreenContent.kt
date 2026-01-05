package com.code.machinecoding.ui.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
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
    isTyping: Boolean,
    onSendText: (String) -> Unit,
    onAttachClick: () -> Unit,
    onImageClick: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()

    val messages = (state as? Response.Success)?.data.orEmpty()
    val imeBottom = rememberImeBottom()
    val isUserAtBottom by remember {
        derivedStateOf {
            val lastVisibleIndex =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            lastVisibleIndex >= messages.lastIndex - 1
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
                    isTyping = isTyping,
                    listState = listState,
                    onImageClick = onImageClick,
                    onLoadMore = onLoadMore,
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

    // Initial load: scroll instantly to bottom
    LaunchedEffect(messages) {
        if (messages.isNotEmpty() && listState.firstVisibleItemIndex == 0) {
            listState.scrollToItem(messages.lastIndex)
        }
    }

    // Scroll to bottom when keyboard opens
    LaunchedEffect(imeBottom) {
        if (imeBottom > 0 && messages.isNotEmpty() && isUserAtBottom) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    // Auto-scroll for new messages only if user is at bottom
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty() && isUserAtBottom) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }
}







