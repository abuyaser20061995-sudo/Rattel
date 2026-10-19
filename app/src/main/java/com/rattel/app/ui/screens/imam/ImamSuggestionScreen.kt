package com.rattel.app.ui.screens.imam

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ImamSuggestionScreen(
    onBackClick: () -> Unit,
    onNavigateToMushaf: (Int) -> Unit,
    viewModel: ImamViewModel
) {
    Text(text = "شاشة مساعد الإمام قيد التجهيز")
}
