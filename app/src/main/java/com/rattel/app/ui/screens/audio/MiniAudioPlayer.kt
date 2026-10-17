package com.rattel.app.ui.screens.audio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rattel.app.core.audio.AudioPlayerManager

@Composable
fun MiniAudioPlayer(
    audioManager: AudioPlayerManager,
    modifier: Modifier = Modifier
) {
    val isPlaying by audioManager.isPlaying.collectAsState()
    val trackTitle by audioManager.currentTrackTitle.collectAsState()

    // لا تظهر المشغل إذا لم يكن هناك صوت قيد التحميل أو التشغيل
    if (trackTitle == "لم يتم تحديد تلاوة") return

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { /* يمكن هنا فتح شاشة كاملة للمشغل */ },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B4332)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "جاري التلاوة",
                    color = Color(0xFFD4AF37), // ذهبي هادئ
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = trackTitle,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // زر الإيقاف والتشغيل
                IconButton(
                    onClick = { if (isPlaying) audioManager.pause() else audioManager.resume() },
                    modifier = Modifier.background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(50))
                ) {
                    Text(if (isPlaying) "⏸" else "▶", color = Color.White) // استخدم أيقونات متجهات (Icons) لاحقاً
                }

                // زر الإغلاق النهائي
                IconButton(onClick = { audioManager.stop() }) {
                    Text("✖", color = Color.Gray)
                }
            }
        }
    }
}
