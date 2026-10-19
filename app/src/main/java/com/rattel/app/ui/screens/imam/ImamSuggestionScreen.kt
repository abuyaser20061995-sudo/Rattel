package com.rattel.app.ui.screens.imam

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rattel.app.domain.usecase.PrayerType
import com.rattel.app.domain.usecase.RecitationLength

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImamSuggestionScreen(
    onBackClick: () -> Unit,
    onNavigateToMushaf: (Int) -> Unit,
    viewModel: ImamViewModel
) {
    val selectedPrayer by viewModel.selectedPrayer.collectAsStateWithLifecycle()
    val selectedLength by viewModel.selectedLength.collectAsStateWithLifecycle()
    val suggestion by viewModel.suggestion.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("مساعد الإمام") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "رجوع")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // قسم اختيار الصلاة
            Text("اختر الصلاة:", style = MaterialTheme.typography.titleMedium)
            PrayerSelectionRow(selectedPrayer = selectedPrayer, onPrayerSelect = { viewModel.setPrayer(it) })

            Spacer(modifier = Modifier.height(8.dp))

            // قسم اختيار طول القراءة
            Text("طول القراءة:", style = MaterialTheme.typography.titleMedium)
            LengthSelectionRow(selectedLength = selectedLength, onLengthSelect = { viewModel.setLength(it) })

            Spacer(modifier = Modifier.height(16.dp))

            // زر توليد الاقتراح
            Button(
                onClick = { viewModel.generateSuggestion() },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("اقترح قراءة للصلاة", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // عرض نتيجة الاقتراح (إذا وجدت)
            if (suggestion != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "الاقتراح:", style = MaterialTheme.typography.titleMedium)
                        Text(text = suggestion!!.surahName, style = MaterialTheme.typography.bodyLarge)
                        Text(text = "من آية ${suggestion!!.startAyah} إلى آية ${suggestion!!.endAyah}")
                        
                        Button(onClick = { onNavigateToMushaf(suggestion!!.pageNumber) }) {
                            Text("فتح في المصحف")
                        }
                    }
                }
            } else {
                Text("اضغط على الزر لاقتراح تلاوة", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

// مكونات فرعية لاختيار الأزرار
@Composable
fun PrayerSelectionRow(selectedPrayer: PrayerType, onPrayerSelect: (PrayerType) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        PrayerType.values().take(3).forEach { prayer ->
            FilterChip(
                selected = selectedPrayer == prayer,
                onClick = { onPrayerSelect(prayer) },
                label = { Text(prayer.name) }
            )
        }
    }
}

@Composable
fun LengthSelectionRow(selectedLength: RecitationLength, onLengthSelect: (RecitationLength) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        RecitationLength.values().forEach { length ->
            FilterChip(
                selected = selectedLength == length,
                onClick = { onLengthSelect(length) },
                label = { Text(length.name) }
            )
        }
    }
}
