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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // اختيار الصلاة
            Text("اختر الصلاة:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                PrayerType.values().take(3).forEach { prayer ->
                    Button(
                        onClick = { viewModel.setPrayer(prayer) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedPrayer == prayer) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(prayer.name)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // اختيار طول القراءة
            Text("طول القراءة:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                RecitationLength.values().forEach { length ->
                    Button(
                        onClick = { viewModel.setLength(length) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedLength == length) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(length.name)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // زر الاقتراح
            Button(
                onClick = { viewModel.generateSuggestion() },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("اقترح تلاوة", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // عرض النتيجة
            if (suggestion != null) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("الاقتراح الحالي:", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(suggestion!!.surahName, style = MaterialTheme.typography.headlineMedium)
                        Text("الآيات: ${suggestion!!.startAyah} - ${suggestion!!.endAyah}")
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { onNavigateToMushaf(suggestion!!.pageNumber) }) {
                            Text("فتح في المصحف")
                        }
                    }
                }
            }
        }
    }
}
