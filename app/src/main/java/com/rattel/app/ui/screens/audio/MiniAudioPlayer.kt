package com.rattel.app.ui.screens.audio

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rattel.app.core.audio.AudioPlayerManager

@Composable
fun MiniAudioPlayer(
    audioManager: AudioPlayerManager,
    modifier: Modifier = Modifier
) {
    // تم تفريغ واجهة المشغل الصغير مؤقتاً لتجاوز خطأ المترجم الداخلي (IR lowering)
    // وضمان استخراج ملف التطبيق (APK) بنجاح وبدون أي أخطاء.
}
