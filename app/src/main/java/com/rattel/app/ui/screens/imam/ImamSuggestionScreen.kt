package com.rattel.app.ui.screens.imam

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ImamSuggestionScreen(
    onBackClick: () -> Unit,
    onNavigateToMushaf: (Int) -> Unit,
    viewModel: ImamViewModel = hiltViewModel()
) {
    Text(text = "شاشة مساعد الإمام قيد التطوير")
}
