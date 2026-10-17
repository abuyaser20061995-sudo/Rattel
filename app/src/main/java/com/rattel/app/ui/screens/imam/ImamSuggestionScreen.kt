package com.rattel.app.ui.screens.imam

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rattel.app.domain.usecase.PrayerType
import com.rattel.app.domain.usecase.RecitationLength

@Composable
fun ImamSuggestionScreen(
    onBackClick: () -> Unit,
    onNavigateToMushaf: (page: Int) -> Unit,
    viewModel: ImamViewModel = hiltViewModel()
) {
    val selectedPrayer by viewModel.selectedPrayer.collectAsStateWithLifecycle()
    val selectedLength by viewModel.selectedLength.collectAsStateWithLifecycle()
    val suggestion by viewModel.suggestion.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("مساعد الإمام", style = MaterialTheme.typography.headlineMedium, color = Color(0xFF1B4332))
        Spacer(modifier = Modifier.height(24.dp))

        // اختيار نوع الصلاة
        Text("اختر الصلاة:", style = MaterialTheme.typography.titleMedium)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            PrayerType.values().take(4).forEach { prayer ->
                FilterChip(
                    selected = selectedPrayer == prayer,
                    onClick = { viewModel.updatePrayer(prayer) },
                    label = { Text(prayer.name) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // اختيار طول القراءة
        Text("طول القراءة:", style = MaterialTheme.typography.titleMedium)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            RecitationLength.values().forEach { length ->
                FilterChip(
                    selected = selectedLength == length,
                    onClick = { viewModel.updateLength(length) },
                    label = { Text(length.name) }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.generateSuggestion() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B4332))
        ) {
            Text("اقترح قراءة للصلاة")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // عرض النتيجة
        suggestion?.let { result ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("الاقتراح:", color = Color.Gray)
                    Text("${result.surahName}", style = MaterialTheme.typography.titleLarge)
                    Text("الآيات: ${result.startAyah} إلى ${result.endAyah}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { onNavigateToMushaf(result.pageNumber) }) {
                        Text("فتح في المصحف")
                    }
                }
            }
        }
    }
}
