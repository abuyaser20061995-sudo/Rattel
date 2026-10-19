package com.rattel.app.ui.screens.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoiceTrackingViewModel @Inject constructor() : ViewModel() {

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    private val _recognizedText = MutableStateFlow("اضغط على الزر لبدء التتبع الصوتي...")
    val recognizedText: StateFlow<String> = _recognizedText.asStateFlow()

    fun toggleListening() {
        if (_isListening.value) {
            stopListening()
        } else {
            startListening()
        }
    }

    private fun startListening() {
        _isListening.value = true
        _recognizedText.value = "جاري الاستماع... (وضع المحاكاة)"
        
        // محاكاة لعملية التعرف على الصوت لتجنب استخدام Vosk في محرك البناء حالياً
        viewModelScope.launch {
            delay(2500) // انتظار ثانيتين ونصف
            if (_isListening.value) {
                _recognizedText.value = "بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيمِ"
            }
        }
    }

    private fun stopListening() {
        _isListening.value = false
        _recognizedText.value = "تم إيقاف الاستماع."
    }
}
