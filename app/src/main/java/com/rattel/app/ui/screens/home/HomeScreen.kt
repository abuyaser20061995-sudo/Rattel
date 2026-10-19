package com.rattel.app.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToMushaf: (Int) -> Unit,
    onNavigateToImamSection: () -> Unit,
    onNavigateToVoiceTracking: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("الرئيسية - رتّل") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onNavigateToMushaf(1) },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("المصحف الشريف", style = MaterialTheme.typography.titleMedium)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onNavigateToImamSection,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("مساعد الإمام", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToVoiceTracking,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("التتبع الصوتي", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
