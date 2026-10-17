package com.rattel.app.ui.screens.downloads

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadsScreen(
    onBackClick: () -> Unit,
    viewModel: DownloadsViewModel = hiltViewModel()
) {
    val downloadedFiles by viewModel.downloadedFiles.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("إدارة التنزيلات") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Text("←") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B4332),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // مثال على زر بدء تحميل تجريبي (في التطبيق الفعلي سيكون هذا الزر داخل شاشة المشغل الصوتي)
            Button(
                onClick = { 
                    viewModel.startDownload(
                        url = "https://example.com/audio/001.mp3", 
                        fileName = "Al_Fatihah_Sudais.mp3"
                    ) 
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D6A4F))
            ) {
                Text("تجربة تحميل سورة الفاتحة")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("الملفات المحملة للعمل بدون إنترنت:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            if (downloadedFiles.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("لا توجد ملفات محملة.", color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(downloadedFiles) { fileItem ->
                        DownloadedFileCard(
                            file = fileItem,
                            onDelete = { viewModel.deleteFile(fileItem.file) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DownloadedFileCard(file: DownloadedFile, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(file.name, style = MaterialTheme.typography.bodyLarge, color = Color(0xFF1B4332))
                Text(String.format("%.2f MB", file.sizeInMb), color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }
            TextButton(onClick = onDelete) {
                Text("حذف", color = Color.Red)
            }
        }
    }
}
