package com.rattel.app.ui.screens.downloads

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import javax.inject.Inject

data class DownloadedFile(val title: String, val file: File)

@HiltViewModel
class DownloadsViewModel @Inject constructor() : ViewModel() {
    
    private val _downloadedFiles = MutableStateFlow<List<DownloadedFile>>(emptyList())
    val downloadedFiles: StateFlow<List<DownloadedFile>> = _downloadedFiles.asStateFlow()

    fun startDownload(url: String, fileName: String) {
        // إضافة ملف وهمي مؤقتاً لاختبار الواجهة قبل ربط محرك التحميل الحقيقي
        val dummyFile = DownloadedFile(title = fileName, file = File("dummy/path/$fileName"))
        _downloadedFiles.value = _downloadedFiles.value + dummyFile
    }

    fun deleteFile(file: File) {
        _downloadedFiles.value = _downloadedFiles.value.filterNot { it.file == file }
    }
}
