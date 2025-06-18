package com.google.mediapipe.examples.llminference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.mediapipe.examples.llminference.services.ModelDownloadService
import com.google.mediapipe.examples.llminference.services.NotificationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

@Composable
internal fun LoadingRoute(
    onModelLoaded: () -> Unit = { },
    onGoBack: () -> Unit = {},
    modelDownloadService: ModelDownloadService,
    notificationService: NotificationService
) {
    val context = LocalContext.current.applicationContext
    var errorMessage by remember { mutableStateOf("") }

    var progress by remember { mutableStateOf(0) }
    var isDownloading by remember { mutableStateOf(false) }
    var job: Job? by remember { mutableStateOf(null) }
    val client = remember { OkHttpClient() }

    if (errorMessage != "") {
        ErrorMessage(errorMessage, onGoBack)
    } else if (isDownloading) {
        DownloadIndicator(progress) {
            job?.cancel()
            isDownloading = false

            notificationService.showNotification(context,"Incomplete",InferenceModel.model.name)
            CoroutineScope(Dispatchers.IO).launch {
                modelDownloadService.deleteDownloadedFile(context)
                withContext(Dispatchers.Main) {
                    errorMessage = "Download Cancelled"
                }
            }
        }
    } else {
        LoadingIndicator()
    }

    LaunchedEffect(Unit) {
        job = launch(Dispatchers.IO) {
            try {
                if (!InferenceModel.modelExists(context)) {
                    if (InferenceModel.model.url.isEmpty()) {
                        throw Exception("Download failed due to empty URL")
                    }
                    isDownloading = true
                    modelDownloadService.downloadModel(context, client) { newProgress ->
                        progress = newProgress
                        if (progress == 100)
                            notificationService.showNotification(context,"Completed",InferenceModel.model.name)

                    }
                }
                isDownloading = false
                InferenceModel.resetInstance(context)
                // Notify the UI that the model has finished loading
                withContext(Dispatchers.Main) {
                    onModelLoaded()
                }
            } catch (e: Exception) {
                val error = e.localizedMessage ?: "Unknown Error"
                errorMessage =
                    "${error}, please copy the model manually to ${InferenceModel.model.path}"
            }
        }
    }
}

@Composable
fun DownloadIndicator(progress: Int, onCancel: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Downloading Model: $progress%",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        CircularProgressIndicator(progress = { progress / 100f })
        Button(onClick = onCancel, modifier = Modifier.padding(top = 8.dp)) {
            Text("Cancel")
        }
    }
}

@Composable
fun LoadingIndicator() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.loading_model),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorMessage(
    errorMessage: String, onGoBack: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = onGoBack, modifier = Modifier.padding(top = 16.dp)) {
            Text("Go Back")
        }
    }
}

