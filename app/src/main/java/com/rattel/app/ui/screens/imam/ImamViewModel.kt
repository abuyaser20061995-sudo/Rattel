package com.rattel.app.ui.screens.imam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rattel.app.domain.usecase.ImamSuggestionUseCase
import com.rattel.app.domain.usecase.PrayerType
import com.rattel.app.domain.usecase.RecitationLength
import com.rattel.app.domain.usecase.SuggestionResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImamViewModel @Inject constructor(
    private val suggestionUseCase: ImamSuggestionUseCase
) : ViewModel() {

    private val _selectedPrayer = MutableStateFlow(PrayerType.FAJR)
    val selectedPrayer: StateFlow<PrayerType> = _selectedPrayer.asStateFlow()

    private val _selectedLength = MutableStateFlow(RecitationLength.MEDIUM)
    val selectedLength: StateFlow<RecitationLength> = _selectedLength.asStateFlow()

    private val _suggestion = MutableStateFlow<SuggestionResult?>(null)
    val suggestion: StateFlow<SuggestionResult?> = _suggestion.asStateFlow()

    fun updatePrayer(prayer: PrayerType) {
        _selectedPrayer.value = prayer
    }

    fun updateLength(length: RecitationLength) {
        _selectedLength.value = length
    }

    fun generateSuggestion() {
        viewModelScope.launch {
            val result = suggestionUseCase.getSuggestion(
                prayer = _selectedPrayer.value,
                length = _selectedLength.value
            )
            _suggestion.value = result
        }
    }
}
