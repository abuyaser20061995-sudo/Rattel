package com.rattel.app.ui.screens.review

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rattel.app.domain.usecase.ReviewQuality

@Composable
fun MemorizationReviewScreen(
    onBackClick: () -> Unit,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val pendingReviews by viewModel.pendingReviews.collectAsStateWithLifecycle()
    val currentItem by viewModel.currentItem.collectAsStateWithLifecycle()
    
    // حالة لإخفاء/إظهار نص الآيات لاختبار الحفظ
    var isTextRevealed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // شريط التقدم والمعلومات
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("المتبقي: ${pendingReviews.size}", color = Color.Gray)
            Text("جلسة المراجعة", style = MaterialTheme.typography.titleMedium)
            IconButton(onClick = onBackClick) { /* أيقونة الإغلاق */ }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (currentItem != null) {
            // البطاقة التي تخفي النص القرآني
            Card(
                onClick = { isTextRevealed = !isTextRevealed },
                modifier = Modifier.fillMaxWidth().weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F0))
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    if (isTextRevealed) {
                        // هنا يتم عرض النص الفعلي للآيات المحفوظة بناءً على startAyahId و endAyahId
                        Text(
                            text = "بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيمِ...", 
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        Text(
                            text = "اضغط لإظهار الآيات",
                            color = Color.Gray,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // أزرار التقييم لا تظهر إلا بعد إظهار النص
            if (isTextRevealed) {
                Text("كيف كان حفظك لهذا المقطع؟", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    ReviewButton("نسيت", Color(0xFFE63946)) {
                        viewModel.submitReview(ReviewQuality.AGAIN)
                        isTextRevealed = false
                    }
                    ReviewButton("صعب", Color(0xFFF4A261)) {
                        viewModel.submitReview(ReviewQuality.HARD)
                        isTextRevealed = false
                    }
                    ReviewButton("جيد", Color(0xFF2A9D8F)) {
                        viewModel.submitReview(ReviewQuality.GOOD)
                        isTextRevealed = false
                    }
                    ReviewButton("متقن", Color(0xFF1B4332)) {
                        viewModel.submitReview(ReviewQuality.EASY)
                        isTextRevealed = false
                    }
                }
            }
        } else {
            // شاشة الانتهاء من المراجعة
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("أتممت مراجعة اليوم!", style = MaterialTheme.typography.headlineMedium, color = Color(0xFF1B4332))
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onBackClick) { Text("العودة للرئيسية") }
                }
            }
        }
    }
}

@Composable
fun ReviewButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier.width(80.dp)
    ) {
        Text(text)
    }
}
