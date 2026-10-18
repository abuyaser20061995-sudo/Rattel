package com.rattel.app.ui.screens.tracking

import androidx.lifecycle.ViewModel
import com.rattel.app.core.audio.VoskVoiceRecognizer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class VoiceTrackingViewModel @Inject constructor(
    private val voskVoiceRecognizer: VoskVoiceRecognizer
) : ViewModel() {
    private val _recognizedText = MutableStateFlow("")
    val recognizedText: StateFlow<String> = _recognizedText

    fun startListening() {
        // تم التعطيل مؤقتاً لتجاوز الخطأ
    }

    fun stopListening() {
        // تم التعطيل مؤقتاً لتجاوز الخطأ
    }
}
