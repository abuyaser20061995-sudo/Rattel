package com.rattel.app.ui.screens.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rattel.app.core.audio.VoskVoiceRecognizer
import com.rattel.app.domain.usecase.TrackedWord
import com.rattel.app.domain.usecase.VoiceMatchingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoiceTrackingViewModel @Inject constructor(
    private val voiceRecognizer: VoskVoiceRecognizer,
    private val matchingUseCase: VoiceMatchingUseCase
) : ViewModel() {

    // قائمة الكلمات للآية المستهدفة
    private val _targetWords = MutableStateFlow<List<TrackedWord>>(emptyList())
    val targetWords: StateFlow<List<TrackedWord>> = _targetWords.asStateFlow()

    private var currentWordIndex = 0

    init {
        // تحميل آية تجريبية للتسميع
        val sampleAyah = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ".split(" ")
        _targetWords.value = sampleAyah.map { TrackedWord(it) }

        // مراقبة ما يقوله المستخدم من المايكروفون
        viewModelScope.launch {
            voiceRecognizer.recognizedText.collect { spokenText ->
                if (spokenText.isNotBlank()) {
                    processSpokenInput(spokenText)
                }
            }
        }
    }

    fun startTracking() {
        voiceRecognizer.startListening()
    }

    fun stopTracking() {
        voiceRecognizer.stopListening()
    }

    private fun processSpokenInput(spokenText: String) {
        val currentWords = _targetWords.value.toMutableList()
        
        currentWordIndex = matchingUseCase.processSpokenText(
            spokenText = spokenText,
            targetWords = currentWords,
            currentIndex = currentWordIndex
        )

        // تحديث الواجهة بالحالة الجديدة للكلمات (أخضر/أحمر)
        _targetWords.value = currentWords.toList()
    }

    override fun onCleared() {
        super.onCleared()
        voiceRecognizer.stopListening()
    }
}
