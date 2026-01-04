package com.code.machinecoding.ui.chat

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.code.machinecoding.ui.viewmodel.ChatViewModel
import com.code.machinecoding.utils.NavRoutes.IMAGE_VIEWER
import com.code.machinecoding.utils.copyUriToCache
import com.code.machinecoding.utils.saveBitmapToCache
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    onImageClick: (String) -> Unit
) {
    val state = viewModel.messagesState.collectAsStateWithLifecycle()
    val isTyping by viewModel.isTyping.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var showAttachmentSheet by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    val galleryLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            uri ?: return@rememberLauncherForActivityResult

           copyUriToCache(context, uri)
                .onSuccess { path ->
                    viewModel.sendImageMessage(
                        imagePath = path,
                        fileSize = File(path).length(),
                        thumbnailPath = null,
                        caption = null
                    )
                }
                .onFailure {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Failed to pick image"
                        )
                    }
                }
        }



    val cameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->
            bitmap ?: return@rememberLauncherForActivityResult

            saveBitmapToCache(context, bitmap)
                .onSuccess { path ->
                    viewModel.sendImageMessage(
                        imagePath = path,
                        fileSize = File(path).length(),
                        thumbnailPath = null,
                        caption = null
                    )
                }
                .onFailure {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Failed to capture image"
                        )
                    }
                }
        }



    ChatScreenContent(
        state = state.value,
        isTyping = isTyping,
        onSendText = viewModel::sendTextMessage,
        onAttachClick = { showAttachmentSheet = true },
        onImageClick = onImageClick,
        snackbarHostState = snackbarHostState
    )

    if (showAttachmentSheet) {
        AttachmentPickerSheet(
            onGalleryClick = {
                showAttachmentSheet = false
                galleryLauncher.launch("image/*")
            },
            onCameraClick = {
                showAttachmentSheet = false
                cameraLauncher.launch()
            },
            onDismiss = {
                showAttachmentSheet = false
            }
        )
    }
}

