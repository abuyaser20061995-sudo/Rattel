package com.rattel.app.ui.screens.downloads

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DownloadsScreen(
    onBackClick: () -> Unit,
    viewModel: DownloadsViewModel
) {
    // تم تفريغ الشاشة مؤقتًا لحل خطأ "Backend Internal error"
    // هذا الكود بسيط للغاية ولن يتسبب في انهيار المترجم
    Text(text = "هذه الشاشة تحت التطوير")
}
