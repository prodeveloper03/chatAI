package com.code.machinecoding.ui.chat


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.code.machinecoding.domain.model.ChatMessage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageList(
    messages: List<ChatMessage>,
    isTyping: Boolean,
    listState: LazyListState,
    onImageClick: (String) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    ) {
        items(
            items = messages,
            key = { it.id + it.timestamp }
        ) { message ->

            Box(modifier = Modifier.animateItemPlacement()) {
                MessageBubble(
                    message = message,
                    onImageClick = onImageClick
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        if (isTyping) {
            item(key = "typing_indicator") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    TypingIndicator()
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (messages.isNotEmpty()) {
            item {
                LaunchedEffect(messages.first().id) {
                    val firstVisible = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index
                    if (firstVisible == 0) {
                        onLoadMore()
                    }
                }
            }
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }
}






