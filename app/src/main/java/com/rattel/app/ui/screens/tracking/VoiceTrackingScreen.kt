package com.rattel.app.ui.screens.tracking

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rattel.app.domain.usecase.WordStatus
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun VoiceTrackingScreen(
    onBackClick: () -> Unit,
    viewModel: VoiceTrackingViewModel = hiltViewModel()
) {
    val targetWords by viewModel.targetWords.collectAsStateWithLifecycle()
    
    // طلب صلاحية الميكروفون (ضرورية جداً)
    val micPermissionState = rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("تسميع ومراجعة الحفظ", style = MaterialTheme.typography.headlineMedium, color = Color(0xFF1B4332))
        Spacer(modifier = Modifier.height(32.dp))

        // بناء النص الملون بناءً على حالة كل كلمة
        val annotatedString = buildAnnotatedString {
            targetWords.forEach { word ->
                val color = when (word.status) {
                    WordStatus.CORRECT -> Color(0xFF2D6A4F) // أخضر
                    WordStatus.ERROR, WordStatus.SKIPPED -> Color.Red
                    WordStatus.PENDING -> Color.Gray
                }
                withStyle(style = SpanStyle(color = color, fontSize = 32.sp)) {
                    append("${word.text} ")
                }
            }
        }

        // عرض النص القرآني
        Text(text = annotatedString, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.weight(1f))

        // أزرار التحكم
        if (micPermissionState.status.isGranted) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { viewModel.startTracking() }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B4332))) {
                    Text("ابدأ التسميع")
                }
                Button(onClick = { viewModel.stopTracking() }, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) {
                    Text("إيقاف")
                }
            }
        } else {
            Button(onClick = { micPermissionState.launchPermissionRequest() }) {
                Text("منح صلاحية الميكروفون")
            }
        }
    }
}
