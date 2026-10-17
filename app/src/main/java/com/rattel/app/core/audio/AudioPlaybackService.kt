package com.rattel.app.core.audio

import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * خدمة تشغيل الصوت في الخلفية.
 * ترتبط هذه الخدمة بنظام أندرويد لتعرض إشعار التحكم بالصوت وشاشة القفل.
 */
@AndroidEntryPoint
class AudioPlaybackService : MediaSessionService() {

    private var mediaSession: MediaSession? = null

    // حقن ExoPlayer الذي سيتم تجهيزه في ملف Hilt Module
    @Inject
    lateinit var player: ExoPlayer

    override fun onCreate() {
        super.onCreate()

        // ضبط خصائص الصوت ليتوقف عند ورود مكالمة هاتفية
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_SPEECH)
            .setUsage(C.USAGE_MEDIA)
            .build()
        
        player.setAudioAttributes(audioAttributes, true)

        // إنشاء جلسة الوسائط (MediaSession) لربط المشغل بالنظام
        mediaSession = MediaSession.Builder(this, player).build()
    }

    // تُستدعى من النظام للحصول على الجلسة الحالية
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
}
