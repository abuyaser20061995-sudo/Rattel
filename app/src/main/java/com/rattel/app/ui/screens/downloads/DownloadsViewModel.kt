package com.rattel.app.ui.screens.downloads

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.rattel.app.core.audio.DownloadWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class DownloadedFile(val name: String, val sizeInMb: Double, val file: File)

@HiltViewModel
class DownloadsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _downloadedFiles = MutableStateFlow<List<DownloadedFile>>(emptyList())
    val downloadedFiles: StateFlow<List<DownloadedFile>> = _downloadedFiles.asStateFlow()

    private val audioDirectory = File(context.filesDir, "quran_audio")

    init {
        loadDownloadedFiles()
    }

    private fun loadDownloadedFiles() {
        viewModelScope.launch {
            if (audioDirectory.exists()) {
                val files = audioDirectory.listFiles()?.map { file ->
                    DownloadedFile(
                        name = file.name,
                        sizeInMb = file.length() / (1024.0 * 1024.0),
                        file = file
                    )
                } ?: emptyList()
                _downloadedFiles.value = files
            }
        }
    }

    fun startDownload(url: String, fileName: String) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val inputData = Data.Builder()
            .putString("FILE_URL", url)
            .putString("FILE_NAME", fileName)
            .build()

        val downloadRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context).enqueue(downloadRequest)
        
        // يمكن مراقبة حالة الـ WorkManager لاحقاً لتحديث الواجهة بشريط التقدم
    }

    fun deleteFile(file: File) {
        if (file.exists() && file.delete()) {
            loadDownloadedFiles() // تحديث القائمة بعد الحذف
        }
    }
}
