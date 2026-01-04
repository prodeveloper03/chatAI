package com.code.machinecoding.ui.chat

import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrowseGallery
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentPickerSheet(
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = "Attach",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            ListItem(
                headlineContent = { Text("Gallery") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.BrowseGallery,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable {
                    onGalleryClick()
                    onDismiss()
                }
            )

            ListItem(
                headlineContent = { Text("Camera") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Filled.Camera,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable {
                    onCameraClick()
                    onDismiss()
                }
            )
        }
    }
}
