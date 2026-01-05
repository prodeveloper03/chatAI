package com.code.machinecoding.ui.chat

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.sp
import com.code.machinecoding.domain.model.ChatMessage
import com.code.machinecoding.utils.MessageType
import com.code.machinecoding.utils.NavRoutes.IMAGE_VIEWER
import com.code.machinecoding.utils.Sender
import com.code.machinecoding.utils.formatTimestamp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageBubble(
    message: ChatMessage,
    onImageClick: (String) -> Unit
) {
    val context = LocalContext.current
    val isUser = message.sender == Sender.USER
    val clipboardManager = LocalClipboardManager.current


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = if (isUser)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
                .widthIn(max = 280.dp)
                .combinedClickable(
                    onClick = {},
                    onLongClick = {
                        if (message.type == MessageType.TEXT) {
                            clipboardManager.setText(AnnotatedString(message.message))
                            Toast
                                .makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                )
        ) {

            when (message.type) {
                MessageType.TEXT -> {
                    Text(
                        text = message.message,
                        color = Color.White
                    )
                }

                MessageType.FILE -> {
                    ImageMessage(
                        message = message,
                        onImageClick = onImageClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = formatTimestamp(message.timestamp),
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
