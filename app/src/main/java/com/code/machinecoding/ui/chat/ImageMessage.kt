package com.code.machinecoding.ui.chat


import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.code.machinecoding.domain.model.ChatMessage
import com.code.machinecoding.utils.formatFileSize
import com.code.machinecoding.utils.saveImageFromUrl
import com.code.machinecoding.utils.saveImageToGallery

@Composable
fun ImageMessage(
    message: ChatMessage,
    onImageClick: (String) -> Unit
) {
    val context = LocalContext.current
    val file = message.file ?: return
    val imagePath = file.thumbnail?.path ?: file.path
    val fullFilePath = file.path

    Column {
        Box {
            AsyncImage(
                model = imagePath,
                contentDescription = null,
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onImageClick(fullFilePath) },
                contentScale = ContentScale.Crop
            )

            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = "Download",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .clickable {
                        if (fullFilePath.startsWith("http")) {
                            saveImageFromUrl(context, fullFilePath)
                        } else {
                            val bitmap = BitmapFactory.decodeFile(fullFilePath)
                            bitmap?.let { saveImageToGallery(context, it) }
                        }
                    }
            )
        }

        if (message.message.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = message.message,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = formatFileSize(file.fileSize),
            fontSize = 10.sp,
            color = Color.Gray
        )
    }
}


