package com.rattel.app.core.audio

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlayerManager @Inject constructor(
    @ApplicationContext private val context: Context,
    val player: ExoPlayer
) {
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentTrackTitle = MutableStateFlow("لم يتم تحديد تلاوة")
    val currentTrackTitle: StateFlow<String> = _currentTrackTitle.asStateFlow()

    init {
        // مراقبة حالة المشغل (يعمل، متوقف، يحمل)
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }
        })
    }

    /**
     * تشغيل سورة أو مقطع محدد
     */
    fun playAudio(url: String, surahName: String, reciterName: String) {
        val mediaItem = MediaItem.Builder()
            .setUri(url)
            .setMediaId(url)
            .build()

        _currentTrackTitle.value = "$surahName - $reciterName"

        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    fun pause() {
        player.pause()
    }

    fun resume() {
        player.play()
    }

    fun stop() {
        player.stop()
        player.clearMediaItems()
    }

    /**
     * الانتقال إلى آية محددة بناءً على التوقيت الزمني (بالملي ثانية)
     */
    fun seekToPosition(positionMs: Long) {
        player.seekTo(positionMs)
    }
}
