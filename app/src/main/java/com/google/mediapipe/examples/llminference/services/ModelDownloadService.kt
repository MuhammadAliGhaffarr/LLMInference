package com.google.mediapipe.examples.llminference.services

import android.content.Context
import com.google.mediapipe.examples.llminference.InferenceModel.Companion.model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream

class ModelDownloadService {

    fun downloadModel(context: Context, client: OkHttpClient, onProgressUpdate: (Int) -> Unit) {
        val outputFile = File(context.filesDir, File(model.path).name)
        val request = Request.Builder().url(model.url).build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw Exception("Download failed: ${response.code}")

        response.body?.byteStream()?.use { inputStream ->
            FileOutputStream(outputFile).use { outputStream ->
                val buffer = ByteArray(4096)
                var bytesRead: Int
                var totalBytesRead = 0L
                val contentLength = response.body?.contentLength() ?: -1

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    totalBytesRead += bytesRead
                    val progress = if (contentLength > 0) {
                        (totalBytesRead * 100 / contentLength).toInt()
                    } else {
                        -1
                    }
                    onProgressUpdate(progress)
                }
                outputStream.flush()
            }
        }
    }

    private fun savedIntoSpecificDirectory(){
        // Extract file extension
        val fileName: String = model.url.substring(model.url.lastIndexOf('/') + 1)
        val extension = if (fileName.contains(".")) fileName.substring(fileName.lastIndexOf('.')) else ".dat"


//    val source = response.body?.source()
//    val file = File.createTempFile(
//        "${model.name}_",
//        ".bin",
//        Environment.getExternalStoragePublicDirectory(
//            Environment.DIRECTORY_DOWNLOADS
//        )
//    )
//    val sink = file.sink().buffer()
//    source.use { input ->
//        sink.use { output ->
//            if (input != null) {
//                output.writeAll(input)
//            }
//        }
//    }
    }

    suspend fun deleteDownloadedFile(context: Context) {
        withContext(Dispatchers.IO) {
            val outputFile = File(context.filesDir, File(model.path).name)
            if (outputFile.exists()) {
                outputFile.delete()
            }
        }
    }
}