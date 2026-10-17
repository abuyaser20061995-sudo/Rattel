package com.rattel.app.ui.screens.tafseer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rattel.app.data.repository.TafseerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ContentType { TAFSEER, IRAB }

@HiltViewModel
class TafseerViewModel @Inject constructor(
    private val repository: TafseerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val ayahId: Int = savedStateHandle.get<Int>("ayahId") ?: 1

    private val _contentType = MutableStateFlow(ContentType.TAFSEER)
    val contentType: StateFlow<ContentType> = _contentType.asStateFlow()

    private val _selectedSourceId = MutableStateFlow(1) // 1 = التفسير الميسر افتراضياً
    val selectedSourceId: StateFlow<Int> = _selectedSourceId.asStateFlow()

    private val _contentText = MutableStateFlow("جاري التحميل...")
    val contentText: StateFlow<String> = _contentText.asStateFlow()

    private val _fontSize = MutableStateFlow(20f)
    val fontSize: StateFlow<Float> = _fontSize.asStateFlow()

    init {
        loadContent()
    }

    fun setContentType(type: ContentType) {
        _contentType.value = type
        loadContent()
    }

    fun setSource(sourceId: Int) {
        _selectedSourceId.value = sourceId
        if (_contentType.value == ContentType.TAFSEER) loadContent()
    }

    fun increaseFontSize() {
        if (_fontSize.value < 36f) _fontSize.value += 2f
    }

    fun decreaseFontSize() {
        if (_fontSize.value > 14f) _fontSize.value -= 2f
    }

    private fun loadContent() {
        viewModelScope.launch {
            if (_contentType.value == ContentType.TAFSEER) {
                val tafseer = repository.getTafseerForAyah(ayahId, _selectedSourceId.value)
                _contentText.value = tafseer?.text ?: "التفسير غير متوفر لهذه الآية في هذا المصدر."
            } else {
                _contentText.value = repository.getIrabForAyah(ayahId)
            }
        }
    }
}
